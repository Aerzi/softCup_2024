package com.example.backend.service;

import com.example.backend.model.entity.aippt.*;

public interface AIPPTService {
    public AIPPTTemplate getTemplateList();
    public AIPPTOutlineResponse outline(AIPPTOutlineRequest request);
    public AIPPTOutlineResponse outlineByDoc(AIPPTOutlineByDocRequest request,String file_url,String file_name);
    public AIPPTResponse ppt(AIPPTRequest request);
    public AIPPTResponse pptByDoc(AIPPTByDocRequest request,String file_url,String file_name);
    public AIPPTProgressResult checkProgress(String sid);
    public void validateParameters(String... params);
}
