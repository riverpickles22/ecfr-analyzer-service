package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.request.WordCountBatchRequest;
import com.ecfranalyzer.dto.response.GetWordCountResponse;

public interface ECFRWordCountService {
    GetWordCountResponse getWordCountByDateTitleChapter(String date, String title, String chapter);
    GetWordCountResponse getWordCountsForMultipleTitleChapters(WordCountBatchRequest request);
} 