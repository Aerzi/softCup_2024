package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.model.entity.Class;
import com.example.backend.mapper.ClassMapper;
import com.example.backend.model.request.teacher.aclass.ClassPageRequest;
import com.example.backend.service.ClassService;
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
 * @since 2024-07-14 10:50:20
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    private final ClassMapper classMapper;

    @Autowired
    public ClassServiceImpl(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    @Override
    public void insertByFilter(Class aclass) {
        classMapper.insert(aclass);
    }

    @Override
    public Class select(Integer id) {
        return classMapper.selectById(id);
    }

    @Override
    public List<Class> list() {
        QueryWrapper<Class> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper.eq("deleted",0);
        return classMapper.selectList(classQueryWrapper);
    }

    @Override
    public PageInfo<Class> page(ClassPageRequest request) {
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(request.getId()!= null,Class::getId,request.getId())
                .eq(request.getName()!=null,Class::getName,request.getName())
                .like(request.getDescription()!=null,Class::getDescription,request.getDescription())
                .ge(request.getCreateTime()!=null,Class::getCreateTime,request.getCreateTime())
                .eq(request.getModifyTime()!=null,Class::getModifyTime,request.getModifyTime())
                .eq(request.getStatus()!=null,Class::getStatus,request.getStatus())
                .eq(request.getUserId()!=null,Class::getUserId,request.getUserId())
                .eq(Class::getDeleted,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                classMapper.selectList(classQueryWrapper)
        );
    }

    @Override
    public PageInfo<Class> page(com.example.backend.model.request.student.aclass.ClassPageRequest request) {
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(request.getId()!= null,Class::getId,request.getId())
                .eq(request.getName()!=null,Class::getName,request.getName())
                .like(request.getDescription()!=null,Class::getDescription,request.getDescription())
                .ge(request.getCreateTime()!=null,Class::getCreateTime,request.getCreateTime())
                .eq(request.getModifyTime()!=null,Class::getModifyTime,request.getModifyTime())
                .eq(request.getStatus()!=null,Class::getStatus,request.getStatus())
                .eq(request.getUserId()!=null,Class::getUserId,request.getUserId())
                .eq(Class::getDeleted,0);
        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                classMapper.selectList(classQueryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(Class request) {
        classMapper.updateById(request);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<Class> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Class::getId,id)
                .set(Class::getDeleted,1);
        classMapper.update(null,queryWrapper);
    }

}
