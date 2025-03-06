package com.ecfranalyzer.service;

import com.ecfranalyzer.dto.request.WordCountBatchRequest;
import com.ecfranalyzer.dto.response.GetHistoricalChangesResponse;
import com.ecfranalyzer.dto.response.GetWordCountResponse;

public interface ECFRService {
    GetWordCountResponse getWordCountByDateTitleChapter(String date, String title, String chapter);
    GetWordCountResponse getWordCountsForMultipleTitleChapters(WordCountBatchRequest request);
    GetHistoricalChangesResponse getHistoricalChanges(String date, String title, String chapter);
} 