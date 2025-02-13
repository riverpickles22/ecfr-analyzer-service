package com.ecfranalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecfranalyzer.dto.response.GetWordCountByAgencyShortNameResponse;
import com.ecfranalyzer.service.ECFRService;

@RestController
@RequestMapping("/wordcount")
public class WordCountController {
    // analyze it for items such as word count per agency

    private final ECFRService ecfrService;

    @Autowired
    public WordCountController(ECFRService ecfrService) {
        this.ecfrService = ecfrService;
    }

    @GetMapping(path = "/byagency/{shortName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetWordCountByAgencyShortNameResponse> getWordCountByAgencyShortName(@PathVariable String shortName) {
        return ResponseEntity.ok(ecfrService.getWordCountByAgencyShortName(shortName));
    }
}
