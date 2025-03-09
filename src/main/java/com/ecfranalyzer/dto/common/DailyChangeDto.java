package com.ecfranalyzer.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyChangeDto {
    private String date;
    private int count;
} 
