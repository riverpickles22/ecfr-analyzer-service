package com.ecfranalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.service.ECFRHistoricalChangesService;
import com.ecfranalyzer.service.impl.ECFRHistoricalChangesServiceImpl;

@RestController
@RequestMapping("/historical-changes")
public class HistoricalChangesController {

    private final ECFRHistoricalChangesService ecfrHistoricalChangesService; 

    @Autowired
    public HistoricalChangesController(ECFRHistoricalChangesServiceImpl ecfrHistoricalChangesService) {
        this.ecfrHistoricalChangesService = ecfrHistoricalChangesService;
    }

    @GetMapping(path = "/{date}/{title}/{chapter}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetHistoricalChangesResponse> getHistoricalChanges(@PathVariable String date, @PathVariable String title, @PathVariable String chapter) {
        return ResponseEntity.ok(ecfrHistoricalChangesService.getHistoricalChanges(date, title, chapter));
    }
}
