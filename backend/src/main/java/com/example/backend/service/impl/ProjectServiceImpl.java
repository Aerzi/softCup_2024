package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.model.entity.Project;
import com.example.backend.mapper.ProjectMapper;
import com.example.backend.model.request.student.project.ProjectPageRequest;
import com.example.backend.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-17 09:35:01
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public void insertByFilter(Project project) {
        projectMapper.insert(project);
    }

    @Override
    public Project select(Integer id) {
        return projectMapper.selectById(id);
    }

    @Override
    public PageInfo<Project> page(ProjectPageRequest request) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getId()!=null,Project::getId,request.getId())
                .eq(request.getName()!=null,Project::getName,request.getName())
                .eq(request.getDescription()!=null,Project::getDescription,request.getDescription())
                .eq(request.getNums()!=null,Project::getNums,request.getNums())
                .eq(request.getFinishedCondition()!=null,Project::getFinishedCondition,request.getFinishedCondition())
                .eq(request.getClassId()!=null,Project::getClassId,request.getClassId())
                .eq(request.getUserId()!=null,Project::getUserId,request.getUserId())
                .eq(Project::getDeleted,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                projectMapper.selectList(queryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(Project project) {
        projectMapper.updateById(project);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<Project> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Project::getId,id)
                .set(Project::getDeleted,1);
        projectMapper.update(null,updateWrapper);
    }


}
