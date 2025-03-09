package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetLatestDateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ECFRAdminService {
    GetAgencyResponse getAllAgencyNamesAndDetails();
    GetLatestDateResponse getLatestDate() throws JsonMappingException, JsonProcessingException;
} 