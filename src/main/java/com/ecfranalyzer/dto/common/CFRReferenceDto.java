package com.ecfranalyzer.dto.common;

import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class CFRReferenceDto {
    
    private Integer title;
    
    private String chapter;
}
