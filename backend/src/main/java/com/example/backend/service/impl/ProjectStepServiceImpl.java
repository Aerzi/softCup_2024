package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.model.entity.ProjectStep;
import com.example.backend.mapper.ProjectStepMapper;
import com.example.backend.model.request.student.projectstep.ProjectStepPageRequest;
import com.example.backend.service.ProjectStepService;
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
 * @since 2024-07-17 03:22:14
 */
@Service
public class ProjectStepServiceImpl extends ServiceImpl<ProjectStepMapper, ProjectStep> implements ProjectStepService {
    private final ProjectStepMapper projectStepMapper;

    @Autowired
    public ProjectStepServiceImpl(ProjectStepMapper projectStepMapper) {
        this.projectStepMapper = projectStepMapper;
    }

    @Override
    public void insertByFilter(ProjectStep projectStep) {
        projectStepMapper.insert(projectStep);
    }

    @Override
    public ProjectStep select(Integer id) {
        return projectStepMapper.selectById(id);
    }

    @Override
    public PageInfo<ProjectStep> page(ProjectStepPageRequest request) {
        LambdaQueryWrapper<ProjectStep> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getId()!=null,ProjectStep::getId,request.getId())
                .like(request.getDescription()!=null,ProjectStep::getDescription,request.getDescription())
                .eq(request.getAssess()!=null,ProjectStep::getAssess,request.getAssess())
                .eq(request.getFinished()!=null,ProjectStep::getFinished,request.getFinished())
                .eq(request.getProjId()!=null,ProjectStep::getProjId,request.getProjId())
                .eq(ProjectStep::getDeleted,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                projectStepMapper.selectList(queryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(ProjectStep projectStep) {
        projectStepMapper.updateById(projectStep);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<ProjectStep> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProjectStep::getId,id)
                .set(ProjectStep::getDeleted,1);
        projectStepMapper.update(null,updateWrapper);
    }
}
