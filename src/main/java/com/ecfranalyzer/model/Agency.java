package com.ecfranalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("short_name")
    private String shortName;
    
    @JsonProperty("display_name")
    private String displayName;
    
    @JsonProperty("sortable_name")
    private String sortableName;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("children")
    private List<Agency> children;
    
    @JsonProperty("cfr_references")
    private List<CFRReference> cfrReferences;

} 