package com.ecfranalyzer.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyHistoricalChangesCounter {
    
    private Map<String, Integer> dates;
}
