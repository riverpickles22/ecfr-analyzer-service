package com.ecfranalyzer.dto.response;

import java.util.List;

import com.ecfranalyzer.model.CFRReference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCFRReferencesResponse {
    private List<CFRReference> cfrReferences;
}