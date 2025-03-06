package com.ecfranalyzer.dto.request;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCountBatchRequest {
    private String date;
    private List<TitleChapterGroup> titleChapters;
} 