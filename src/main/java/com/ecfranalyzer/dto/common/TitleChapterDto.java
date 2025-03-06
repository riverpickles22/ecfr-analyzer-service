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
public class TitleChapterDto {

    private Integer title;
    
    private List<String> chapters;
} 