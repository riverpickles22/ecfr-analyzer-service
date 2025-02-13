package com.ecfranalyzer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFRReference {
    @JsonProperty("title")
    private Integer title;
    
    @JsonProperty("chapter")
    private String chapter;
}
