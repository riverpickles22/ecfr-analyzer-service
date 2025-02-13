package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.dto.response.GetWordCountByAgencyShortNameResponse;
import com.ecfranalyzer.service.ECFRService;

import org.springframework.stereotype.Service;

@Service
public class ECFRServiceImpl implements ECFRService {

    @Override
    public GetWordCountByAgencyShortNameResponse getWordCountByAgencyShortName(String shortName) {
        return null;
    }

    @Override
    public GetHistoricalChangesResponse getHistoricalChanges() {
        return null;
    }
}
