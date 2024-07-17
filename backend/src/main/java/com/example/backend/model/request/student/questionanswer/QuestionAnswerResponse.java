package com.example.backend.model.request.student.questionanswer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerResponse {
    private Integer id;

    private String answer;

    private Integer score;

    private Integer userId;

    private Integer classId;
}
