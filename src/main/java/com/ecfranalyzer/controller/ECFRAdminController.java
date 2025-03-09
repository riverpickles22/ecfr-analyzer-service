package com.ecfranalyzer.controller;

import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetLatestDateResponse;
import com.ecfranalyzer.service.ECFRAdminService;
import com.ecfranalyzer.service.impl.ECFRAdminServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ECFRAdminController {

    private final ECFRAdminService ecfrAdminService;

    @Autowired
    public ECFRAdminController(ECFRAdminServiceImpl ecfrAdminService) {
        this.ecfrAdminService = ecfrAdminService;
    }

    /**
     * Get all agencies
     * @return
     */
    @GetMapping(path = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAgencyResponse> getAllAgencyNamesAndDetails() {
        return ResponseEntity.ok(ecfrAdminService.getAllAgencyNamesAndDetails());
    }

    @GetMapping(path = "/latest-date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetLatestDateResponse> getLatestDate() {
        try {
            return ResponseEntity.ok(ecfrAdminService.getLatestDate());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 