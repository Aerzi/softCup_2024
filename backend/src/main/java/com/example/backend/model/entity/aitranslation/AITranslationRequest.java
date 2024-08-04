package com.example.backend.model.entity.aitranslation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AITranslationRequest {
    @NotBlank
    private String text;
}
