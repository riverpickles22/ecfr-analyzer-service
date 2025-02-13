package com.ecfranalyzer.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetWordCountByAgencyShortNameResponse {
    private Long wordCount;
}
