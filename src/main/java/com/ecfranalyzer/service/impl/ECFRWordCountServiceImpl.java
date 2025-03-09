package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.request.WordCountBatchRequest;
import com.ecfranalyzer.dto.response.GetWordCountResponse;
import com.ecfranalyzer.service.ECFRWordCountService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;

@Service
public class ECFRWordCountServiceImpl implements ECFRWordCountService {

    private static final Logger logger = LoggerFactory.getLogger(ECFRWordCountServiceImpl.class);
    private static final Pattern XML_TAG_PATTERN = Pattern.compile("<[^>]*>");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    private final ECFRClient ecfrClient;

    @Value("${ecfr.threadpool.size:1}")
    private int threadPoolSize;

    @Autowired
    public ECFRWordCountServiceImpl(ECFRClient ecfrClient) {
        this.ecfrClient = ecfrClient;
    }

    @Override
    public GetWordCountResponse getWordCountByDateTitleChapter(String date, String title, String chapter) {
        logger.info("Getting word count for date: {}, title: {}, chapter: {}", date, title, chapter);
        long totalWordCount = fetchAndCountWords(date, title, chapter);
        return GetWordCountResponse.builder()
            .wordCount(totalWordCount)
            .build();
    }

    @Override
    public GetWordCountResponse getWordCountsForMultipleTitleChapters(WordCountBatchRequest request) {
        if (request == null || request.getTitleChapters() == null || request.getDate() == null) {
            logger.error("Invalid batch request: {}", request);
            return GetWordCountResponse.builder().wordCount(0L).build();
        }
        
        logger.info("Processing batch word count request for date: {}, with {} title-chapter pairs", 
                   request.getDate(), request.getTitleChapters().size());
        
        Long totalWordCount = calculateTotalWordCount(request);

        return GetWordCountResponse.builder()
            .wordCount(totalWordCount)
            .build();
    }

    private Long calculateTotalWordCount(WordCountBatchRequest request) {
        return request.getTitleChapters().parallelStream()
            .flatMapToLong(titleChapter -> 
                titleChapter.getChapters().parallelStream()
                    .mapToLong(chapter -> 
                        fetchAndCountWords(request.getDate(), titleChapter.getTitle(), chapter)
                    )
            )
            .sum();
    }

    /**
     * This method:
     * 1. Retrieves the entire title structure from the new API endpoint.
     * 2. Locates the branch for the given chapter.
     * 3. If the chapter (or any node in its branch) is reserved, that branch is skipped.
     * 4. Recursively counts the words in each node's "label" in the branch.
     * 5. Finds all non-reserved part nodes, fetches their XML content in parallel, and counts the words.
     * 6. Returns the sum of the label word count and the XML content word counts.
     */
    private Long fetchAndCountWords(String date, String title, String chapter) {
        try {
            // Retrieve the entire title structure and locate the chapter branch.
            JsonNode chapterNode = getChapterStructure(date, title, chapter);
            if (chapterNode == null) {
                logger.warn("Chapter structure not found for date: {}, title: {}, chapter: {}", date, title, chapter);
                return 0L;
            }
            // If the chapter itself is reserved, return 0.
            if (chapterNode.has("reserved") && chapterNode.get("reserved").asBoolean(true)) {
                logger.warn("Reserved chapter for date: {}, title: {}, chapter: {}", date, title, chapter);
                return 0L;
            }
            
            // Count the words in every node's label in the branch.
            long labelWordCount = countLabelWords(chapterNode);
            logger.debug("Label word count for date: {}, title: {}, chapter: {} = {}", date, title, chapter, labelWordCount);
            // Find all non-reserved part nodes in the branch.
            List<JsonNode> partNodes = findPartNodes(chapterNode);
            long xmlWordCount = countWordsInParts(partNodes, date, title);
            logger.debug("XML word count for date: {}, title: {}, chapter: {} = {}", date, title, chapter, xmlWordCount);
            return labelWordCount + xmlWordCount;
        } catch (Exception e) {
            logger.error("Unexpected error processing date: {}, title: {}, chapter: {}", date, title, chapter, e);
            return 0L;
        }
    }
    
    /**
     * Retrieves the title structure JSON and returns the JsonNode corresponding
     * to the specified chapter.
     */
    private JsonNode getChapterStructure(String date, String title, String chapter) {
        String urlPath = "/structure/" + date + "/title-" + title + ".json";
        String responseJSON = ecfrClient.fetchContent(ecfrClient.getVersionerApiUrl(urlPath), String.class);
    
        if (responseJSON == null || responseJSON.trim().isEmpty()) {
            logger.warn("Empty response received for date: {}, title: {}, chapter: {}", date, title, chapter);
            return null;
        }
    
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseJSON);
            // Recursively search for the chapter node.
            return findChapterNode(rootNode, chapter);
        } catch (Exception e) {
            logger.error("Error parsing JSON for date: {}, title: {}, chapter: {}", date, title, chapter, e);
            return null;
        }
    }
    
    /**
     * Recursively searches for a node that is a chapter with the matching identifier.
     */
    private JsonNode findChapterNode(JsonNode node, String chapter) {
        if (node.has("type") && node.get("type").asText().equalsIgnoreCase("chapter") &&
            node.has("identifier") && node.get("identifier").asText().equals(chapter)) {
            return node;
        }
        if (node.has("children") && node.get("children").isArray()) {
            for (JsonNode child : node.get("children")) {
                JsonNode found = findChapterNode(child, chapter);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
    
    /**
     * Recursively finds all non-reserved nodes of type "part" within the given node.
     */
    private List<JsonNode> findPartNodes(JsonNode node) {
        List<JsonNode> parts = new ArrayList<>();
        if (node == null) {
            return parts;
        }
        // Skip reserved nodes.
        if (node.has("reserved") && node.get("reserved").asBoolean(true)) {
            return parts;
        }
        if (node.has("type") && node.get("type").asText().equalsIgnoreCase("part")) {
            parts.add(node);
        }
        if (node.has("children") && node.get("children").isArray()) {
            for (JsonNode child : node.get("children")) {
                parts.addAll(findPartNodes(child));
            }
        }
        return parts;
    }
    
    /**
     * Recursively counts the words in the "label" property for the given node and its children,
     * skipping any node (and its branch) that is reserved.
     */
    private long countLabelWords(JsonNode node) {
        if (node == null) {
            return 0L;
        }
        if (node.has("reserved") && node.get("reserved").asBoolean(true)) {
            return 0L;
        }
        long count = 0L;
        if (node.has("label")) {
            String label = node.get("label").asText();
            String[] words = WHITESPACE_PATTERN.split(label.trim());
            count += (words.length > 0 && !words[0].isEmpty()) ? words.length : 0;
        }
        if (node.has("children") && node.get("children").isArray()) {
            for (JsonNode child : node.get("children")) {
                count += countLabelWords(child);
            }
        }
        return count;
    }

    /**
     * Uses parallel processing (with a custom ForkJoinPool) to fetch and count words in the XML content for each part node.
     */
    @SuppressWarnings("resource")
    private Long countWordsInParts(List<JsonNode> partNodes, String date, String title) {
        if (partNodes.isEmpty()) {
            return 0L;
        }
        
        ForkJoinPool customThreadPool = new ForkJoinPool(threadPoolSize);
        try {
            return customThreadPool.submit(() ->
                partNodes.parallelStream()
                    .mapToLong(partNode -> {
                        String partIdentifier = partNode.path("identifier").asText();
                        String urlPath = "/full/" + date + "/title-" + title + ".xml?part=" + partIdentifier;
                        String partXml = ecfrClient.fetchContent(ecfrClient.getVersionerApiUrl(urlPath), String.class);
                        if (partXml == null || partXml.trim().isEmpty()) {
                            logger.warn("Empty XML content for date: {}, title: {}, part: {}", date, title, partIdentifier);
                            return 0L;
                        }
                        return countWordsInXml(partXml);
                    })
                    .sum()
            ).join();
        } finally {
            customThreadPool.shutdown();
        }
    }

    private Long countWordsInXml(String xml) {
        String textContent = XML_TAG_PATTERN.matcher(xml).replaceAll("");
        String[] words = WHITESPACE_PATTERN.split(textContent.trim());
        return words.length > 0 && !words[0].isEmpty() ? (long) words.length : 0L;
    }
}
