package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;

public interface ECFRHistoricalChangesService {
    GetHistoricalChangesResponse getHistoricalChanges(String date, String title, String chapter);
} 