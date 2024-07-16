package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.model.entity.Question;
import com.example.backend.mapper.QuestionMapper;
import com.example.backend.model.request.question.QuestionPageRequest;
import com.example.backend.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-15 06:27:04
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public void insertByFilter(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public Question select(Integer id) {
        return questionMapper.selectById(id);
    }

    @Override
    public List<Question> list(){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        return questionMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Question> page(QuestionPageRequest request) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getId()!=null,Question::getId,request.getId())
                .eq(request.getName()!=null,Question::getName,request.getName())
                .eq(request.getType()!=null,Question::getType,request.getType())
                .like(request.getDescription()!=null,Question::getDescription,request.getDescription())
                .like(request.getContent()!=null,Question::getContent,request.getContent())
                .eq(request.getScore()!=null,Question::getScore,request.getScore())
                .eq(request.getDifficult()!=null,Question::getDifficult,request.getDifficult())
                .eq(request.getCorrect()!=null,Question::getCorrect,request.getCorrect())
                .ge(request.getCreateTime()!=null,Question::getCreateTime,request.getCreateTime())
                .ge(request.getModifyTime()!=null,Question::getModifyTime,request.getModifyTime())
                .eq(request.getStatus()!=null,Question::getStatus,request.getStatus())
                .eq(Question::getDeleted,0)
                .eq(Question::getClassId,request.getClassId());

        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                questionMapper.selectList(queryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(Question question) {
        questionMapper.updateById(question);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getId,id)
                .eq(Question::getDeleted,1);
        questionMapper.update(null,queryWrapper);
    }

    @Override
    public void deleteByClassIdFilter(Integer classId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        questionMapper.delete(queryWrapper);
    }

}
