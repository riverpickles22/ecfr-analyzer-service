package com.ecfranalyzer.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleChapterGroup {
    private String title;
    private List<String> chapters;  
} 