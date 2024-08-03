package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Question;
import com.example.backend.model.request.question.QuestionAddRequest;
import com.example.backend.model.request.question.QuestionEditRequest;
import com.example.backend.model.request.question.QuestionPageRequest;
import com.example.backend.model.request.question.QuestionResponse;
import com.example.backend.service.QuestionService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/teacher/question")
@RestController("TeacherQuestionController")
public class QuestionController extends BaseApiController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //添加编程题
    @PostMapping("/add")
    public RestResponse add(@RequestBody @Valid QuestionAddRequest request){
        Question question = modelMapper.map(request,Question.class);
        question.setDeleted(false);
        question.setStatus(1);
        questionService.insertByFilter(question);
        return RestResponse.ok();
    }

    @GetMapping("/select/{id}")
    public RestResponse<QuestionResponse> select(@PathVariable Integer id){
        Question question = questionService.select(id);
        QuestionResponse response = modelMapper.map(question,QuestionResponse.class);
        response.setCreateTime(DateTimeUtil.dateFormat(question.getCreateTime()));
        response.setModifyTime(DateTimeUtil.dateFormat(question.getModifyTime()));
        return RestResponse.ok(response);
    }

    @GetMapping("/list")
    public RestResponse<List<Question>> list(){
        List<Question> questions = questionService.list();
        return RestResponse.ok(questions);
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

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid QuestionEditRequest request){
        Question question = modelMapper.map(request,Question.class);
        questionService.updateByIdFilter(question);
        return RestResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        questionService.deleteByIdFilter(id);
        return RestResponse.ok();
    }
}
