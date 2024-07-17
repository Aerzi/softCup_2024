package com.example.backend.service;

import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.judge.JudgeResult;

public interface JudgeService {
    public String createSubmission(String json);
    public JudgeResult getJudgeResult(String token);
    public RestResponse<String> getJudgeStatus();
    public RestResponse<String> getJudgeLanguage();
}
