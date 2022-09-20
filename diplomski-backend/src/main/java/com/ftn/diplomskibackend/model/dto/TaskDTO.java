package com.ftn.diplomskibackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO {
    private Long id;
    private String question;
    private String answer;
    private Long lessonId;
}
