package com.ecfranalyzer.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLatestDateResponse {
    private String latestDate;
}
