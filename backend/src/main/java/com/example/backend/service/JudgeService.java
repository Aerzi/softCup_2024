package com.example.backend.service;

import com.example.backend.base.RestResponse;

public interface JudgeService {
    public String createSubmission(String json);
    public RestResponse<String> getJudgeResult(String token);
    public RestResponse<String> getJudgeStatus();
    public RestResponse<String> getJudgeLanguage();
}
