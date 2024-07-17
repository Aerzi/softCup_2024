package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.model.entity.QuestionUserAnswer;
import com.example.backend.mapper.QuestionUserAnswerMapper;
import com.example.backend.model.request.admin.question.answer.QuestionAnswerPageRequest;
import com.example.backend.model.request.admin.question.answer.QuestionEditRequest;
import com.example.backend.model.request.student.questionanswer.QuestionAnswerConditionRequest;
import com.example.backend.service.QuestionUserAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-15 09:20:38
 */
@Service
public class QuestionUserAnswerServiceImpl extends ServiceImpl<QuestionUserAnswerMapper, QuestionUserAnswer> implements QuestionUserAnswerService {
    private final QuestionUserAnswerMapper questionUserAnswerMapper;

    @Autowired
    public QuestionUserAnswerServiceImpl(QuestionUserAnswerMapper questionUserAnswerMapper) {
        this.questionUserAnswerMapper = questionUserAnswerMapper;
    }

    @Override
    public void insertByFilter(QuestionUserAnswer questionUserAnswer) {
        questionUserAnswerMapper.insert(questionUserAnswer);
    }

    @Override
    public PageInfo<QuestionUserAnswer> conditionPage(QuestionAnswerConditionRequest request) {
        LambdaQueryWrapper<QuestionUserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getUserId()!=null,QuestionUserAnswer::getUserId,request.getUserId())
                .eq(request.getQuestionId()!=null,QuestionUserAnswer::getQuestionId,request.getQuestionId())
                .eq(request.getClassId()!=null,QuestionUserAnswer::getClassId,request.getClassId());
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                questionUserAnswerMapper.selectList(queryWrapper)
        );
    }

    @Override
    public PageInfo<QuestionUserAnswer> errorPage(QuestionAnswerConditionRequest request) {
        LambdaQueryWrapper<QuestionUserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getUserId()!=null,QuestionUserAnswer::getUserId,request.getUserId())
                .eq(request.getQuestionId()!=null,QuestionUserAnswer::getQuestionId,request.getQuestionId())
                .eq(request.getClassId()!=null,QuestionUserAnswer::getClassId,request.getClassId())
                .eq(QuestionUserAnswer::getScore,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                questionUserAnswerMapper.selectList(queryWrapper)
        );
    }

    @Override
    public PageInfo<QuestionUserAnswer> page(QuestionAnswerPageRequest request) {
        LambdaQueryWrapper<QuestionUserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getId()!=null,QuestionUserAnswer::getId,request.getId())
                .like(request.getAnswer()!=null,QuestionUserAnswer::getAnswer,request.getAnswer())
                .eq(request.getScore()!=null,QuestionUserAnswer::getScore,request.getScore())
                .eq(request.getUserId()!=null,QuestionUserAnswer::getUserId,request.getUserId())
                .eq(request.getQuestionId()!=null,QuestionUserAnswer::getQuestionId,request.getQuestionId())
                .eq(QuestionUserAnswer::getDeleted,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                questionUserAnswerMapper.selectList(queryWrapper)
        );
    }

    @Override
    public QuestionUserAnswer select(Integer id) {
        return questionUserAnswerMapper.selectById(id);
    }

    @Override
    public void edit(QuestionUserAnswer questionUserAnswer) {
        questionUserAnswerMapper.updateById(questionUserAnswer);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<QuestionUserAnswer> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(QuestionUserAnswer::getUserId,id)
                .set(QuestionUserAnswer::getDeleted,1);
        questionUserAnswerMapper.update(null,queryWrapper);
    }
}
