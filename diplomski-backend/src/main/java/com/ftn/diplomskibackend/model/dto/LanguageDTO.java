package com.ftn.diplomskibackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageDTO {
    private Long id;
    private String name;
}
