package com.example.backend.service;

import com.example.backend.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.question.QuestionPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-15 06:27:04
 */
public interface QuestionService extends IService<Question> {
    public void insertByFilter(Question question);
    public Question select(Integer id);
    public List<Question> list();
    public PageInfo<Question> page(QuestionPageRequest request);
    public void updateByIdFilter(Question question);
    public void deleteByIdFilter(Integer id);
    public void deleteByClassIdFilter(Integer classId);
}
