package com.ecfranalyzer.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetHistoricalChangesResponse {
    private String date;
    private String title;
    private String chapter;
}
