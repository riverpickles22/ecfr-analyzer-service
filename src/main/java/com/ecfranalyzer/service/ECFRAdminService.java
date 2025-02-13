package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.response.GetAgencyResponse;
import com.ecfranalyzer.dto.response.GetCFRReferencesResponse;

public interface ECFRAdminService {
    GetAgencyResponse getAllAgencies();
    GetCFRReferencesResponse getCFRReferencesByShortName(String agencyShortName);
} 