package com.ftn.diplomskibackend.model.dto;

import com.ftn.diplomskibackend.model.ELessonType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LessonDTO {
    private Long id;
    private ELessonType lessonType;
    private List<TaskDTO> tasks;
}
