package com.example.backend.service;

import com.example.backend.model.entity.aippt.AIPPTOutlineRequest;
import com.example.backend.model.entity.aippt.AIPPTOutlineResponse;
import com.example.backend.model.entity.aippt.AIPPTTemplate;

public interface AIPPTService {
    public AIPPTTemplate getTemplateList();
    public AIPPTOutlineResponse outline(AIPPTOutlineRequest request);
    public void validateParameters(String... params);
}
