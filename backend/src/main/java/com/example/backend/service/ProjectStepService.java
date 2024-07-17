package com.example.backend.service;

import com.example.backend.model.entity.ProjectStep;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.student.projectstep.ProjectStepPageRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-17 03:22:14
 */
public interface ProjectStepService extends IService<ProjectStep> {
    public void insertByFilter(ProjectStep projectStep);
    public ProjectStep select(Integer id);
    public PageInfo<ProjectStep> page(ProjectStepPageRequest request);
    public void updateByIdFilter(ProjectStep projectStep);
    public void deleteByIdFilter(Integer id);
}
