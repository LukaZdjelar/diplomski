package com.ftn.diplomskibackend.model.dto;

import com.ftn.diplomskibackend.model.ELessonStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonProgressDTO {
    private Long id;
    private LessonDTO lessons;
    private ELessonStatus lessonStatus;
}
