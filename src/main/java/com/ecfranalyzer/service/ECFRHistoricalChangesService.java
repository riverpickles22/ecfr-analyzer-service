package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ECFRHistoricalChangesService {
    GetHistoricalChangesResponse getHistoricalChangesBySlug(String slug) throws JsonMappingException, JsonProcessingException;
} 