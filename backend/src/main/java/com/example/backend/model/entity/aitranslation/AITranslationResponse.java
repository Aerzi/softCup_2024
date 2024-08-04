package com.example.backend.model.entity.aitranslation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITranslationResponse {
    private AITranslationHeader header;
    private AITranslationPayload payload;
}
