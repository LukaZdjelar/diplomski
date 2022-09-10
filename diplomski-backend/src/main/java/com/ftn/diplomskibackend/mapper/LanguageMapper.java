package com.ftn.diplomskibackend.mapper;

import com.ftn.diplomskibackend.model.Language;
import com.ftn.diplomskibackend.model.dto.LanguageDTO;

import java.util.List;
import java.util.stream.Collectors;

public class LanguageMapper {
    public static Language mapModel(LanguageDTO languageDTO){
        return Language.builder()
                .id(languageDTO.getId())
                .name(languageDTO.getName())
                .build();
    }
    public static LanguageDTO mapDTO(Language language){
        return LanguageDTO.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }
    public static List<Language> mapListToModel(List<LanguageDTO> languages){
        List<Language> list = languages.stream()
                .map(language -> mapModel(language))
                .collect(Collectors.toList());
        return list;
    }
    public static List<LanguageDTO> mapListToDTO(List<Language> languages){
        List<LanguageDTO> dtoList = languages.stream()
                .map(language -> mapDTO(language))
                .collect(Collectors.toList());
        return dtoList;
    }
}
