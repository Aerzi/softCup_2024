package com.example.backend.model.request.student.programmingassess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgrammingAssessEditRequest {
    private String feedback;

    private String learningProgress;

    private String errorAnalysis;

    private String optimizationSuggestions;

    private Integer robustnessScore;

    private Integer readabilityScore;

    private Integer efficiencyScore;

    private Integer completenessScore;

    private Integer correctnessScore;

    private Integer overallScore;

    private Integer questionId;
}
