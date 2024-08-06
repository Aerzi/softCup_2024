package com.example.backend.model.entity.aitranslation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AITranslationDocResponse {
    private List<String> texts;
    private String url;
}
