package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.ProgrammingAssess;
import com.example.backend.model.entity.Question;
import com.example.backend.model.entity.QuestionUserAnswer;
import com.example.backend.model.entity.assess.programming.AssessProgramming;
import com.example.backend.model.entity.judge.JudgeResult;
import com.example.backend.model.entity.result.ProgrammingAssessResult;
import com.example.backend.model.enums.QuestionTypeEnum;
import com.example.backend.model.request.student.judge.JudgeRequest;
import com.example.backend.model.request.student.judge.JudgeSubmitRequest;
import com.example.backend.service.JudgeService;
import com.example.backend.service.ProgrammingAssessService;
import com.example.backend.service.QuestionUserAnswerService;
import com.example.backend.service.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/student/judge")
@RestController("StudentJudgeController")
public class JudgeController extends BaseApiController {
    private final JudgeService judgeService;
    private final QuestionUserAnswerService questionUserAnswerService;
    private final ProgrammingAssessService programmingAssessService;
    private final WebSocketService webSocketService;
    private final SystemConfig systemConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new Gson();

    @Autowired
    public JudgeController(JudgeService judgeService, QuestionUserAnswerService questionUserAnswerService, ProgrammingAssessService programmingAssessService, WebSocketService webSocketService, SystemConfig systemConfig) {
        this.judgeService = judgeService;
        this.questionUserAnswerService = questionUserAnswerService;
        this.programmingAssessService = programmingAssessService;
        this.webSocketService = webSocketService;
        this.systemConfig = systemConfig;
    }

    @PostMapping("/submit")
    public RestResponse<ProgrammingAssessResult> submit(@RequestBody @Valid JudgeSubmitRequest request) {
        String json = null;

        JudgeRequest judgeRequest = request.getRequest();
        json = gson.toJson(judgeRequest);

        String token = judgeService.createSubmission(json);
        JudgeResult result = judgeService.getJudgeResult(token);

        // 解码Base64字符串
        byte[] decodedBytes = Base64.getDecoder().decode(result.getSource_code());

        // 将字节数组转换为字符串
        String decodedString = new String(decodedBytes);

        // 输出解码后的字符串
//        System.out.println("Decoded string: " + decodedString);
        AssessProgramming assessProgramming = new AssessProgramming();
        assessProgramming.setSource_code(decodedString);
        // Todo 获取大模型反参并插入到数据库中
        //解析source_code
        String receivedMessage = null;
        ProgrammingAssessResult programmingAssessResult = null;
        try {
            webSocketService.connect(systemConfig.getWebSocketPropertyConfig().getUrl());
            webSocketService.sendMessage(gson.toJson(assessProgramming));

            receivedMessage = webSocketService.getReceivedMessage();
            programmingAssessResult = objectMapper.readValue(receivedMessage, ProgrammingAssessResult.class);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//
//        ProgrammingAssess programmingAssess = modelMapper.map(programmingAssessResult,ProgrammingAssess.class);
        ProgrammingAssess programmingAssess = new ProgrammingAssess();
        if(programmingAssessResult.getFeedback() != null)
            programmingAssess.setFeedback(programmingAssessResult.getFeedback());
        if(programmingAssessResult.getModified_code() != null)
            programmingAssess.setModifiedCode(programmingAssessResult.getModified_code());
        if (programmingAssessResult.getError_analysis() != null)
            programmingAssess.setErrorAnalysis(programmingAssessResult.getError_analysis());
        if (programmingAssessResult.getOptimization_suggestions() != null)
            programmingAssess.setOptimizationSuggestions(programmingAssessResult.getOptimization_suggestions());

        programmingAssessService.insertByFilter(programmingAssess);

        //插入学生用户答题
        QuestionUserAnswer questionUserAnswer = new QuestionUserAnswer();
        questionUserAnswer.setUserId(getCurrentUser().getId());
        questionUserAnswer.setClassId(request.getClassId());
        questionUserAnswer.setQuestionId(request.getQuestionId());
//        questionUserAnswer.setScore(programmingAssess.getOverallScore());
        questionUserAnswer.setDeleted(false);

        questionUserAnswerService.insertByFilter(questionUserAnswer);

        return RestResponse.ok(programmingAssessResult);
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
