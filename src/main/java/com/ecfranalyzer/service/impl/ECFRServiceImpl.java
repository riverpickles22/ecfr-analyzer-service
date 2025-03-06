package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.request.WordCountBatchRequest;
import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.dto.response.GetWordCountResponse;
import com.ecfranalyzer.service.ECFRService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

@Service
public class ECFRServiceImpl implements ECFRService {

    private static final Logger logger = LoggerFactory.getLogger(ECFRServiceImpl.class);
    private static final Pattern XML_TAG_PATTERN = Pattern.compile("<[^>]*>");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    private final ECFRClient ecfrClient;

    @Autowired
    public ECFRServiceImpl(ECFRClient ecfrClient) {
        this.ecfrClient = ecfrClient;
    }

    @Override
    public GetWordCountResponse getWordCountByDateTitleChapter(String date, String title, String chapter) {
        logger.info("Getting word count for date: {}, title: {}, chapter: {}", date, title, chapter);
        return GetWordCountResponse.builder()
            .wordCount(fetchAndCountWords(date, title, chapter))
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
        
        Long totalWordCount = request.getTitleChapters().stream()
            .flatMapToLong(titleChapter -> 
                titleChapter.getChapters().stream()
                    .mapToLong(chapter -> 
                        fetchAndCountWords(
                            request.getDate(), 
                            titleChapter.getTitle(), 
                            chapter
                        )
                    )
            )
            .sum();

        return GetWordCountResponse.builder()
            .wordCount(totalWordCount)
            .build();
    }

    @Override
    public GetHistoricalChangesResponse getHistoricalChanges(String date, String title, String chapter) {
        logger.warn("Historical changes feature not yet implemented for date: {}, title: {}, chapter: {}", 
                   date, title, chapter);
        return null;
    }

    private Long fetchAndCountWords(String date, String title, String chapter) {
        String urlPath = "/full/" + date + "/title-" + title + ".xml?chapter=" + chapter;
        String responseXML = null;
        
        try {
            logger.info("Fetching XML content from: {}", urlPath);
            ResponseEntity<String> response = ecfrClient.getRestTemplate()
                    .exchange(
                            ecfrClient.getVersionerApiUrl(urlPath),
                            HttpMethod.GET,
                            null,
                            String.class
                    );
            
            responseXML = response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching data for date: {}, title: {}, chapter: {}: {}", 
                        date, title, chapter, e.getMessage());
            return 0L;
        }
        
        if (responseXML == null || responseXML.trim().isEmpty()) {
            logger.warn("Empty response received for date: {}, title: {}, chapter: {}", 
                       date, title, chapter);
            return 0L;
        }
        
        return countWordsInXml(responseXML);
    }
    
    private Long countWordsInXml(String xml) {
        logger.info("Original # words in XML string: {}", xml.length());
        // Remove XML tags
        String textContent = XML_TAG_PATTERN.matcher(xml).replaceAll("");
        logger.info("After removing XML tags: {}", textContent.length());
        // Normalize whitespace and split by spaces
        String[] words = WHITESPACE_PATTERN.split(textContent.trim());
        logger.info("After splitting: {}", words.length);
        // Count non-empty words
        return words.length > 0 && !words[0].isEmpty() ? (long) words.length : 0L;
    }
}
