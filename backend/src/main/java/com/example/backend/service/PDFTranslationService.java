package com.example.backend.service;

import com.example.backend.model.entity.aitranslation.AITranslationText;

public interface PDFTranslationService {
    public AITranslationText translate(String text);
}
