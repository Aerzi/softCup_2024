package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.model.entity.ProgrammingAssess;
import com.example.backend.mapper.ProgrammingAssessMapper;
import com.example.backend.model.request.student.programmingassess.ProgrammingAssessSelectOneRequest;
import com.example.backend.service.ProgrammingAssessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-18 11:25:45
 */
@Service
public class ProgrammingAssessServiceImpl extends ServiceImpl<ProgrammingAssessMapper, ProgrammingAssess> implements ProgrammingAssessService {
    private final ProgrammingAssessMapper programmingAssessMapper;

    @Autowired
    public ProgrammingAssessServiceImpl(ProgrammingAssessMapper programmingAssessMapper) {
        this.programmingAssessMapper = programmingAssessMapper;
    }

    @Override
    public void insertByFilter(ProgrammingAssess programmingAssess) {
        programmingAssessMapper.insert(programmingAssess);
    }

    @Override
    public ProgrammingAssess select(ProgrammingAssessSelectOneRequest request) {
        LambdaQueryWrapper<ProgrammingAssess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getQuestionId()!=null,ProgrammingAssess::getQuestionId,request.getQuestionId())
                        .eq(request.getUserId()!=null,ProgrammingAssess::getUserId,request.getUserId());
        return programmingAssessMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateByIdFilter(ProgrammingAssess programmingAssess) {
        programmingAssessMapper.updateById(programmingAssess);
    }
}
