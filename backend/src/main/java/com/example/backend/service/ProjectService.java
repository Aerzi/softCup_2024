package com.example.backend.service;

import com.example.backend.model.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.student.project.ProjectPageRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-17 09:35:01
 */
public interface ProjectService extends IService<Project> {
    public void insertByFilter(Project project);
    public Project select(Integer id);
    public PageInfo<Project> page(ProjectPageRequest request);
    public void updateByIdFilter(Project project);
    public void deleteByIdFilter(Integer id);
}
