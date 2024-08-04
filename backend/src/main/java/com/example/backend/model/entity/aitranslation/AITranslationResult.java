package com.example.backend.model.entity.aitranslation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITranslationResult {
    private String seq;
    private String status;
    private String text;
}
