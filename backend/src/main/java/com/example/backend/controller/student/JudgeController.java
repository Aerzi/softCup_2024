package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Question;
import com.example.backend.model.entity.QuestionUserAnswer;
import com.example.backend.model.entity.judge.JudgeResult;
import com.example.backend.model.enums.QuestionTypeEnum;
import com.example.backend.model.request.student.judge.JudgeRequest;
import com.example.backend.model.request.student.judge.JudgeSubmitRequest;
import com.example.backend.service.JudgeService;
import com.example.backend.service.QuestionUserAnswerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequestMapping("/api/student/judge")
@RestController("StudentJudgeController")
public class JudgeController extends BaseApiController {
    private final JudgeService judgeService;
    private final QuestionUserAnswerService questionUserAnswerService;
    private static final Gson gson = new Gson();

    @Autowired
    public JudgeController(JudgeService judgeService, QuestionUserAnswerService questionUserAnswerService) {
        this.judgeService = judgeService;
        this.questionUserAnswerService = questionUserAnswerService;
    }

    @PostMapping("/submit")
    public RestResponse<JudgeResult> submit(@RequestBody @Valid JudgeSubmitRequest request) {
        String json = null;

        JudgeRequest judgeRequest = request.getRequest();
        json = gson.toJson(judgeRequest);

        String token = judgeService.createSubmission(json);
        JudgeResult result = judgeService.getJudgeResult(token);

        QuestionUserAnswer questionUserAnswer = new QuestionUserAnswer();
        questionUserAnswer.setUserId(request.getUserId());
        questionUserAnswer.setQuestionId(request.getQuestionId());
        questionUserAnswer.setDeleted(false);

        questionUserAnswerService.insertByFilter(questionUserAnswer);

        return RestResponse.ok(result);
    }

    @GetMapping("/status")
    public RestResponse<String> status() {
        return judgeService.getJudgeStatus();
    }

    @GetMapping("/lang")
    public RestResponse<String> lang() {
        return judgeService.getJudgeLanguage();
    }
}
