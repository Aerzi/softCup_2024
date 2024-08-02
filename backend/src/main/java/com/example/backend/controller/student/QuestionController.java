package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Question;
import com.example.backend.model.request.question.QuestionPageRequest;
import com.example.backend.model.request.question.QuestionResponse;
import com.example.backend.service.QuestionService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/student/question")
@RestController("StudentQuestionController")
public class QuestionController extends BaseApiController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

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
}
