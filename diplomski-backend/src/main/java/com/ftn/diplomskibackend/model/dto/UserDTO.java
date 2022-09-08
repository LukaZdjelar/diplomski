package com.ftn.diplomskibackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean isAdministrator;
    private List<LessonProgressDTO> lessonProgresses;
}