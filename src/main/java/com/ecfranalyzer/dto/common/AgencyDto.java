package com.ecfranalyzer.dto.common;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgencyDto {
    
    private String shortName;
    
    private String displayName;
    
    private String sortableName;
    
    private String slug;

    private List<AgencyDto> children;

    private List<TitleChapterDto> titleChapters;


}
