package com.ecfranalyzer.service.mapper;

import com.ecfranalyzer.dto.common.AgencyDto;
import com.ecfranalyzer.dto.common.TitleChapterDto;
import com.ecfranalyzer.model.Agency;
import com.ecfranalyzer.model.CFRReference;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AgencyMapper {

    public static List<AgencyDto> convertAgenciesToAgencyDtos(List<Agency> agencies) {
        return agencies.stream()
            .map(AgencyMapper::convertAgencyToAgencyDto)
            .collect(Collectors.toList());
    }

    private static AgencyDto convertAgencyToAgencyDto(Agency agency) {
        return AgencyDto.builder()
            .displayName(agency.getDisplayName())
            .shortName(agency.getShortName())
            .sortableName(agency.getSortableName())
            .children(agency.getChildren() != null 
                ? convertAgenciesToAgencyDtos(agency.getChildren()) 
                : Collections.emptyList())
            .slug(agency.getSlug())
            .titleChapters(convertCfrReferencesToTitleChapterDtos(agency.getCfrReferences()))
            .build();
    }

    private static List<TitleChapterDto> convertCfrReferencesToTitleChapterDtos(List<CFRReference> cfrReferences) {
        return cfrReferences.stream()
            .collect(Collectors.groupingBy(
                CFRReference::getTitle,
                Collectors.mapping(CFRReference::getChapter, Collectors.toList())
            ))
            .entrySet().stream()
            .map(entry -> TitleChapterDto.builder()
                .title(entry.getKey())
                .chapters(entry.getValue())
                .build())
            .collect(Collectors.toList());
    }
}
