package com.ecfranalyzer.controller;

import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetCFRReferencesResponse;
import com.ecfranalyzer.service.ECFRAdminService;
import com.ecfranalyzer.service.impl.ECFRAdminServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecfr/admin")
public class ECFRAdminController {

    private final ECFRAdminService ecfrAdminService;

    @Autowired
    public ECFRAdminController(ECFRAdminServiceImpl ecfrAdminService) {
        this.ecfrAdminService = ecfrAdminService;
    }

    @GetMapping(path = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAgencyResponse> getAllAgencies() {
        return ResponseEntity.ok(ecfrAdminService.getAllAgencies());
    }

    @GetMapping(path = "/agencies/get-cfr-references/{shortName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCFRReferencesResponse> getCFRReferencesByShortName(@PathVariable String shortName) {
        return ResponseEntity.ok(ecfrAdminService.getCFRReferencesByShortName(shortName));
    }
} 