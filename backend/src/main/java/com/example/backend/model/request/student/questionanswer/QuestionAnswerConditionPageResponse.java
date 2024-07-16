package com.example.backend.model.request.student.questionanswer;

import com.example.backend.model.request.question.QuestionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerConditionPageResponse {
    private QuestionAnswerResponse questionAnswerResponse;

    private QuestionResponse questionResponse;
}
