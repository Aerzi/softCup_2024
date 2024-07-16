package com.example.backend.controller.admin;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.QuestionUserAnswer;
import com.example.backend.model.request.admin.question.answer.QuestionAnswerPageRequest;
import com.example.backend.model.request.admin.question.answer.QuestionAnswerResponse;
import com.example.backend.model.request.admin.question.answer.QuestionEditRequest;
import com.example.backend.model.request.question.QuestionAddRequest;
import com.example.backend.service.QuestionUserAnswerService;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/admin/question/answer")
@RestController("AdminQuestionAnswerController")
public class QuestionAnswerController extends BaseApiController {
    private final QuestionUserAnswerService questionUserAnswerService;

    @Autowired
    public QuestionAnswerController(QuestionUserAnswerService questionUserAnswerService) {
        this.questionUserAnswerService = questionUserAnswerService;
    }

    @PostMapping("/add")
    public RestResponse add(@RequestBody @Valid QuestionAddRequest request) {
        QuestionUserAnswer questionUserAnswer = modelMapper.map(request, QuestionUserAnswer.class);
        questionUserAnswer.setDeleted(false);
        questionUserAnswerService.insertByFilter(questionUserAnswer);
        return RestResponse.ok();
    }

    @GetMapping("/select/{id}")
    public RestResponse<QuestionAnswerResponse> select(@PathVariable Integer id) {
        QuestionUserAnswer questionUserAnswer = questionUserAnswerService.select(id);
        QuestionAnswerResponse response = modelMapper.map(questionUserAnswer, QuestionAnswerResponse.class);
        return RestResponse.ok(response);

    }

    @PostMapping("/page")
    public RestResponse<PageInfo<QuestionAnswerResponse>> page(@RequestBody @Valid QuestionAnswerPageRequest request) {
        PageInfo<QuestionUserAnswer> pageInfo = questionUserAnswerService.page(request);
        PageInfo<QuestionAnswerResponse> page = PageInfoHelper.copyMap(pageInfo, e -> modelMapper.map(e, QuestionAnswerResponse.class)
        );
        return RestResponse.ok(page);
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody QuestionEditRequest request){
        QuestionUserAnswer questionUserAnswer = modelMapper.map(request,QuestionUserAnswer.class);
        questionUserAnswerService.edit(questionUserAnswer);
        return RestResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        questionUserAnswerService.deleteByIdFilter(id);
        return RestResponse.ok();
    }
}
