package com.example.backend.service;

import com.example.backend.model.entity.ProgrammingAssess;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.student.programmingassess.ProgrammingAssessSelectOneRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-18 11:25:45
 */
public interface ProgrammingAssessService extends IService<ProgrammingAssess> {
    public void insertByFilter(ProgrammingAssess programmingAssess);
    public ProgrammingAssess select(ProgrammingAssessSelectOneRequest request);
    public void updateByIdFilter(ProgrammingAssess programmingAssess);
    public void updateByQuestionAndUserIdFilter(ProgrammingAssessSelectOneRequest request,ProgrammingAssess programmingAssess);
}
