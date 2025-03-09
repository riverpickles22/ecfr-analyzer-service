package com.ecfranalyzer.dto.response;

import java.util.List;

import com.ecfranalyzer.dto.common.DailyChangeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHistoricalChangesResponse {
    private List<DailyChangeDto> dailyChanges;
    private Integer totalChanges;
}