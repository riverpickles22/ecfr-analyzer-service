package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.dto.common.DailyChangeDto;
import com.ecfranalyzer.model.DailyHistoricalChangesCounter;
import com.ecfranalyzer.service.ECFRHistoricalChangesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public GetHistoricalChangesResponse getHistoricalChangesBySlug(String slug)
            throws JsonMappingException, JsonProcessingException {
    
        logger.info("Getting historical changes for slug: {}", slug);
        String urlPath = "/counts/daily?agency_slugs[]=" + slug;
    
        DailyHistoricalChangesCounter response = ecfrClient.fetchContent(ecfrClient.getSearchApiUrl(urlPath), DailyHistoricalChangesCounter.class);
        if (response == null) {
            logger.error("No response from ECFR API for slug: {}", slug);
            throw new RuntimeException("No response from ECFR API for slug: " + slug);
        }
    
        // Build a list of (date, count) objects and sort by date
        List<DailyChangeDto> dailyChanges = response.getDates().entrySet().stream()
            .map(entry -> new DailyChangeDto(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparing(e -> LocalDate.parse(e.getDate())))
            .collect(Collectors.toList());
    
        Integer totalChanges = dailyChanges.stream()
            .mapToInt(DailyChangeDto::getCount)
            .sum();
        // Return in ascending date order
        return GetHistoricalChangesResponse.builder()
            .dailyChanges(dailyChanges)
            .totalChanges(totalChanges)
            .build();
    }
    
}