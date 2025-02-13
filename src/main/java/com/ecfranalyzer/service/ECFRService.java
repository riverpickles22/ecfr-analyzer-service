package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.dto.response.GetWordCountByAgencyShortNameResponse;

public interface ECFRService {
    GetWordCountByAgencyShortNameResponse getWordCountByAgencyShortName(String shortName);
    GetHistoricalChangesResponse getHistoricalChanges();
} 