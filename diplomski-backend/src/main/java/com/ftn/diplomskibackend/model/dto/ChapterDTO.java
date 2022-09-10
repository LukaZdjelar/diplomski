package com.ftn.diplomskibackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChapterDTO {
    private Long id;
    private String name;
    private Integer level;
    private Boolean isLocked;
    private List<LessonDTO> lessons;
}
