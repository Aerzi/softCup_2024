package com.example.backend.service;

import com.example.backend.model.entity.ProgrammingAssess;
import com.baomidou.mybatisplus.extension.service.IService;

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
    public ProgrammingAssess select(Integer id);
    public void updateByIdFilter(ProgrammingAssess programmingAssess);
}
