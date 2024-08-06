package com.example.backend.model.entity.aitranslation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITranslationText {
    private AITranslationTextResult trans_result;
    private String from;
    private String to;
}
