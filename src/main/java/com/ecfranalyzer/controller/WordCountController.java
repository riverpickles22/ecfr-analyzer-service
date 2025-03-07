package com.ecfranalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecfranalyzer.dto.request.WordCountBatchRequest;
import com.ecfranalyzer.dto.response.GetWordCountResponse;
import com.ecfranalyzer.service.ECFRWordCountService;

@RestController
@RequestMapping("/wordcount")
public class WordCountController {

    private final ECFRWordCountService ecfrService;

    @Autowired
    public WordCountController(ECFRWordCountService ecfrService) {
        this.ecfrService = ecfrService;
    }

    @GetMapping(path = "/date-{date}/title-{title}/chapter-{chapter}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetWordCountResponse> getWordCountByDateTitleChapter(@PathVariable String date, @PathVariable String title, @PathVariable String chapter) {
        return ResponseEntity.ok(ecfrService.getWordCountByDateTitleChapter(date, title, chapter));
    }
    
    @PostMapping(path = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetWordCountResponse> getMultipleTitleChapterWordCounts(@RequestBody WordCountBatchRequest request) {
       return ResponseEntity.ok(ecfrService.getWordCountsForMultipleTitleChapters(request));
   }
}
