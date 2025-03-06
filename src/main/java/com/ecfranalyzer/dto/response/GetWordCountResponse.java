package com.ecfranalyzer.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWordCountResponse {
    private Long wordCount;
}
