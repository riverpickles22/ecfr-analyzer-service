package com.ecfranalyzer.dto.response;

import com.ecfranalyzer.dto.common.AgencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAgencyResponse {
    private List<AgencyDto> agencies;
} 