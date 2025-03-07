package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.service.ECFRHistoricalChangesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ECFRHistoricalChangesServiceImpl implements ECFRHistoricalChangesService {

    private static final Logger logger = LoggerFactory.getLogger(ECFRHistoricalChangesServiceImpl.class);
    
    private final ECFRClient ecfrClient;

    @Autowired
    public ECFRHistoricalChangesServiceImpl(ECFRClient ecfrClient) {
        this.ecfrClient = ecfrClient;
    }

    @Override
    public GetHistoricalChangesResponse getHistoricalChanges(String date, String title, String chapter) {
        logger.info("Getting historical changes for date: {}, title: {}, chapter: {}", date, title, chapter);
        return GetHistoricalChangesResponse.builder()
            .date(date)
            .title(title)
            .chapter(chapter)
            .build();
    }
    
}
