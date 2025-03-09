package com.ecfranalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.service.ECFRHistoricalChangesService;
import com.ecfranalyzer.service.impl.ECFRHistoricalChangesServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/historical-changes")
public class HistoricalChangesController {

    private final ECFRHistoricalChangesService ecfrHistoricalChangesService; 

    @Autowired
    public HistoricalChangesController(ECFRHistoricalChangesServiceImpl ecfrHistoricalChangesService) {
        this.ecfrHistoricalChangesService = ecfrHistoricalChangesService;
    }

    @GetMapping(path = "/slug-{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetHistoricalChangesResponse> getHistoricalChanges(@PathVariable String slug) {
        try {
            return ResponseEntity.ok(ecfrHistoricalChangesService.getHistoricalChangesBySlug(slug));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}