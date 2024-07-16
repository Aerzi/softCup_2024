package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Question;
import com.example.backend.model.entity.QuestionUserAnswer;
import com.example.backend.model.request.question.QuestionPageRequest;
import com.example.backend.model.request.question.QuestionResponse;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerAnswerRequest;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerConditionPageResponse;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerConditionRequest;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerResponse;
import com.example.backend.service.QuestionService;
import com.example.backend.service.QuestionUserAnswerService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-15 06:27:04
 */
@RequestMapping("/api/student/question")
@RestController("StudentQuestionController")
public class QuestionAnswerController extends BaseApiController {
    private final QuestionService questionService;
    private final QuestionUserAnswerService questionUserAnswerService;

    @Autowired
    public QuestionAnswerController(QuestionService questionService, QuestionUserAnswerService questionUserAnswerService) {
        this.questionService = questionService;
        this.questionUserAnswerService = questionUserAnswerService;
    }

    //答题
    @PostMapping("/answer")
    public RestResponse answer(@RequestBody @Valid QuestionAnswerAnswerRequest request){
        QuestionUserAnswer questionUserAnswer = modelMapper.map(request,QuestionUserAnswer.class);
        Question question = questionService.select(request.getQuestionId());
        if(Objects.equals(request.getAnswer(), question.getCorrect())){
            questionUserAnswer.setScore(question.getScore());
        }else{
            questionUserAnswer.setScore(0);
        }
        questionUserAnswer.setDeleted(false);
        questionUserAnswerService.insertByFilter(questionUserAnswer);
        return RestResponse.ok();
    }

    //查询题目
    @PostMapping("/page")
    public RestResponse<PageInfo<QuestionResponse>> page(@RequestBody @Valid QuestionPageRequest request){
        PageInfo<Question> pageInfo = questionService.page(request);
        PageInfo<QuestionResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            QuestionResponse response = modelMapper.map(e, QuestionResponse.class);
            response.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            response.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return response;
        });
        return RestResponse.ok(page);
    }

    //查看自己作答情况
    @PostMapping("/condition")
    public RestResponse<PageInfo<QuestionAnswerConditionPageResponse>> conditionPage(@RequestBody @Valid QuestionAnswerConditionRequest request){
        PageInfo<QuestionUserAnswer> pageInfo = questionUserAnswerService.conditionPage(request);
        PageInfo<QuestionAnswerConditionPageResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            QuestionAnswerConditionPageResponse response = new QuestionAnswerConditionPageResponse();
            QuestionAnswerResponse questionAnswerResponse = modelMapper.map(e,QuestionAnswerResponse.class);
            response.setQuestionAnswerResponse(questionAnswerResponse);
            Question question = questionService.select(e.getQuestionId());
            QuestionResponse questionResponse = modelMapper.map(question,QuestionResponse.class);
            response.setQuestionResponse(questionResponse);
            return response;
        });
        return RestResponse.ok(page);
    }

    //错题本
    @PostMapping("/answer/error")
    public RestResponse<PageInfo<QuestionAnswerConditionPageResponse>> answerErrorPage(@RequestBody @Valid QuestionAnswerConditionRequest request){
        PageInfo<QuestionUserAnswer> pageInfo = questionUserAnswerService.errorPage(request);
        PageInfo<QuestionAnswerConditionPageResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            QuestionAnswerConditionPageResponse response = new QuestionAnswerConditionPageResponse();
            QuestionAnswerResponse questionAnswerResponse = modelMapper.map(e,QuestionAnswerResponse.class);
            response.setQuestionAnswerResponse(questionAnswerResponse);
            Question question = questionService.select(e.getQuestionId());
            QuestionResponse questionResponse = modelMapper.map(question,QuestionResponse.class);
            response.setQuestionResponse(questionResponse);
            return response;
        });
        return RestResponse.ok(page);
    }



}
