package com.example.backend.service;

import com.example.backend.model.entity.aitranslation.AITranslationDocResponse;
import com.example.backend.model.entity.aitranslation.AITranslationText;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public interface PDFTranslationService {
    public AITranslationText translate(String text);
    public AITranslationDocResponse translateDoc(MultipartFile file, int maxBytes, Charset charset);
}
