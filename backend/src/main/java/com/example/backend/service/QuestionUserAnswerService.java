package com.example.backend.service;

import com.example.backend.model.entity.QuestionUserAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.admin.question.answer.QuestionAnswerPageRequest;
import com.example.backend.model.request.admin.question.answer.QuestionEditRequest;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerConditionRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-15 09:20:38
 */
public interface QuestionUserAnswerService extends IService<QuestionUserAnswer> {
    public void insertByFilter(QuestionUserAnswer questionUserAnswer);
    public PageInfo<QuestionUserAnswer> conditionPage(QuestionAnswerConditionRequest request);
    public PageInfo<QuestionUserAnswer> errorPage(QuestionAnswerConditionRequest request);
    public PageInfo<QuestionUserAnswer> page(QuestionAnswerPageRequest request);
    public QuestionUserAnswer select(Integer id);
    public void edit(QuestionUserAnswer questionUserAnswer);
    public void deleteByIdFilter(Integer id);
}
