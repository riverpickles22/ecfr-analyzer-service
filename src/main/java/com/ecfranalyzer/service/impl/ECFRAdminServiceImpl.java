package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.common.AgencyDto;
import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetLatestDateResponse;
import com.ecfranalyzer.model.AgencyList;
import com.ecfranalyzer.service.ECFRAdminService;
import com.ecfranalyzer.service.mapper.AgencyMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ECFRAdminServiceImpl implements ECFRAdminService {

    private static final Logger logger = LoggerFactory.getLogger(ECFRAdminServiceImpl.class);
    private final ECFRClient ecfrClient;

    @Autowired
    public ECFRAdminServiceImpl(ECFRClient ecfrClient) {
        this.ecfrClient = ecfrClient;
    }

    @Override
    public GetAgencyResponse getAllAgencyNamesAndDetails() {
        List<AgencyDto> agencies = getAgencies();
        return GetAgencyResponse.builder()
            .agencies(agencies)
            .build();
    }

    @Override
    public GetLatestDateResponse getLatestDate() throws JsonMappingException, JsonProcessingException {
        String response = ecfrClient.fetchContent(ecfrClient.getVersionerApiUrl("/titles.json"), String.class);

        // Parse the response to get the up_to_date_as_of value from the first title
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode titlesArray = rootNode.get("titles");
        
        if (titlesArray == null || titlesArray.isEmpty()) {
            logger.error("No titles found in the response");
            throw new RuntimeException("No titles found in the response");
        }
        
        JsonNode firstTitle = titlesArray.get(0);
        String upToDateAsOf = firstTitle.get("up_to_date_as_of").asText();

        logger.info("Up to date as of: {}", upToDateAsOf);
        return GetLatestDateResponse.builder()
            .latestDate(upToDateAsOf)
            .build();
    }

    private List<AgencyDto> getAgencies() {
        AgencyList agencies = ecfrClient.fetchContent(ecfrClient.getAdminApiUrl("/agencies.json"), AgencyList.class);
        return AgencyMapper.convertAgenciesToAgencyDtos(agencies.getAgencies());
    }    
} 