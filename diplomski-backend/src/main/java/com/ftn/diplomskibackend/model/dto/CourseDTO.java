package com.ftn.diplomskibackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private LanguageDTO local;
    private LanguageDTO foreign;
    private List<ChapterDTO> chapters;
}
