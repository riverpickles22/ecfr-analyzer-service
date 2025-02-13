package com.ecfranalyzer.service.impl;

import com.ecfranalyzer.clients.ECFRClient;
import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetCFRReferencesResponse;
import com.ecfranalyzer.exception.BadRequestException;
import com.ecfranalyzer.model.Agency;
import com.ecfranalyzer.model.CFRReference;
import com.ecfranalyzer.service.ECFRAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
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
    public GetAgencyResponse getAllAgencies() {
        try {
            return getAgencies();
        } catch (RestClientException e) {
            logger.error("Error fetching all agencies from ECFR API", e);
            throw new BadRequestException("Failed to fetch agencies: " + e.getMessage());
        }
    }

    @Override
    public GetCFRReferencesResponse getCFRReferencesByShortName(String agencyShortName) {
        List<Agency> agencies = getAllAgencies().getAgencies();
        List<CFRReference> allReferences = new ArrayList<>();
        
        // Search through all agencies and their children
        findAndCollectReferences(agencies, agencyShortName.toUpperCase(), allReferences);
        
        if (allReferences.isEmpty()) {
            throw new BadRequestException("Agency not found with short name: " + agencyShortName);
        }
        
        return GetCFRReferencesResponse.builder()
                .cfrReferences(allReferences)
                .build();
    }

    private void findAndCollectReferences(List<Agency> agencies, String targetShortName, List<CFRReference> references) {
        for (Agency agency : agencies) {
            // Check if current agency matches
            if (targetShortName.equals(agency.getShortName() != null ? agency.getShortName().toUpperCase() : "")) {
                collectReferences(agency, references);
                return;
            }
            
            // Check children if they exist
            if (agency.getChildren() != null) {
                findAndCollectReferences(agency.getChildren(), targetShortName, references);
            }
        }
    }

    private void collectReferences(Agency agency, List<CFRReference> references) {
        if (agency.getCfrReferences() != null) {
            references.addAll(agency.getCfrReferences());
        }
        
        if (agency.getChildren() != null) {
            agency.getChildren().forEach(child -> collectReferences(child, references));
        }
    }

    private GetAgencyResponse getAgencies() {
        GetAgencyResponse agencies = ecfrClient.getRestTemplate()
                .exchange(
                        ecfrClient.getAdminApiUrl("/agencies.json"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<GetAgencyResponse>() {}
                ).getBody();
        
        if (agencies == null || agencies.getAgencies() == null) {
            throw new BadRequestException("Received null response from ECFR API");
        }
        
        return agencies;
    }
    
    // Implement other methods similarly...
} 