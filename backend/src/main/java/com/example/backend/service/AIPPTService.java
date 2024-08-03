package com.example.backend.service;

import com.example.backend.model.entity.aippt.AIPPTTemplate;

public interface AIPPTService {
    public AIPPTTemplate getTemplateList();
    public void validateParameters(String... params);
}
