package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.common.AgencyDto;
import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.exception.BadRequestException;
import com.ecfranalyzer.model.AgencyList;
import com.ecfranalyzer.service.ECFRAdminService;
import com.ecfranalyzer.service.mapper.AgencyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

    private List<AgencyDto> getAgencies() {
        try {
            AgencyList agencies = ecfrClient.getRestTemplate()
                    .exchange(
                            ecfrClient.getAdminApiUrl("/agencies.json"),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<AgencyList>() {}
                    ).getBody();
            
            if (agencies == null || agencies.getAgencies().isEmpty()) {
                throw new BadRequestException("Received null response from ECFR API");
            }

            return AgencyMapper.convertAgenciesToAgencyDtos(agencies.getAgencies());
        } catch (Exception e) {
            logger.error("Error fetching agencies: ", e);
            throw e;
        }
    }    
} 