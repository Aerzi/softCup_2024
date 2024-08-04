package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.mapper.ClassStudentMapper;
import com.example.backend.model.entity.Class;
import com.example.backend.mapper.ClassMapper;
import com.example.backend.model.entity.ClassStudent;
import com.example.backend.model.request.student.aclass.ClassResponse;
import com.example.backend.model.request.teacher.aclass.ClassPageRequest;
import com.example.backend.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.ModelMapperSingle;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-14 10:50:20
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
    private final ClassMapper classMapper;
    private final ClassStudentMapper classStudentMapper;
    /**
     * The constant modelMapper.
     */
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @Autowired
    public ClassServiceImpl(ClassMapper classMapper, ClassStudentMapper classStudentMapper) {
        this.classMapper = classMapper;
        this.classStudentMapper = classStudentMapper;
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
    public List<ClassResponse> listClass(Integer studentId) {
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(Class::getDeleted, 0);
        List<Class> classes =  classMapper.selectList(classQueryWrapper);

        List<ClassResponse> classResponses = classes.stream().map(e->{
            ClassResponse classResponse = modelMapper.map(e,ClassResponse.class);
            classResponse.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            classResponse.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return classResponse;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<ClassStudent> classStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classStudentLambdaQueryWrapper.eq(ClassStudent::getUserId, studentId);

        List<ClassStudent> classStudents = classStudentMapper.selectList(classStudentLambdaQueryWrapper);

        for (ClassStudent student : classStudents) {
            int studentClassId = student.getClassId();
            for (ClassResponse clazz : classResponses) {
                if (clazz.getId() == studentClassId) {
                    clazz.setIsAdded(true);
                }else{
                    clazz.setIsAdded(false);
                }
            }
        }
        return classResponses;
    }

    @Override
    public PageInfo<Class> page(ClassPageRequest request) {
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(request.getId() != null, Class::getId, request.getId())
                .eq(request.getName() != null, Class::getName, request.getName())
                .like(request.getDescription() != null, Class::getDescription, request.getDescription())
                .ge(request.getCreateTime() != null, Class::getCreateTime, request.getCreateTime())
                .eq(request.getModifyTime() != null, Class::getModifyTime, request.getModifyTime())
                .eq(request.getStatus() != null, Class::getStatus, request.getStatus())
                .eq(request.getUserId() != null, Class::getUserId, request.getUserId())
                .eq(Class::getDeleted, 0);
        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                classMapper.selectList(classQueryWrapper)
        );
    }

    @Override
    public PageInfo<ClassResponse> page(com.example.backend.model.request.student.aclass.ClassPageRequest request, Integer studentId) {
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(request.getId() != null, Class::getId, request.getId())
                .eq(request.getName() != null, Class::getName, request.getName())
                .like(request.getDescription() != null, Class::getDescription, request.getDescription())
                .ge(request.getCreateTime() != null, Class::getCreateTime, request.getCreateTime())
                .eq(request.getModifyTime() != null, Class::getModifyTime, request.getModifyTime())
                .eq(request.getStatus() != null, Class::getStatus, request.getStatus())
                .eq(request.getUserId() != null, Class::getUserId, request.getUserId())
                .eq(Class::getDeleted, 0);

        PageInfo<Class> pageInfo = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                classMapper.selectList(classQueryWrapper)
        );

        PageInfo<ClassResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ClassResponse response =  modelMapper.map(e, ClassResponse.class);
            response.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            response.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return response;
        });

        List<ClassResponse> classes = page.getList();

        LambdaQueryWrapper<ClassStudent> classStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classStudentLambdaQueryWrapper.eq(ClassStudent::getUserId, studentId);

        List<ClassStudent> classStudents = classStudentMapper.selectList(classStudentLambdaQueryWrapper);

        for (ClassStudent student : classStudents) {
            int studentClassId = student.getClassId();
            for (ClassResponse clazz : classes) {
                if (clazz.getId() == studentClassId) {
                    clazz.setIsAdded(true);
                }else{
                    clazz.setIsAdded(false);
                }
            }
        }

        return page;
    }

    @Override
    public void updateByIdFilter(Class request) {
        classMapper.updateById(request);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<Class> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Class::getId, id)
                .set(Class::getDeleted, 1);
        classMapper.update(null, queryWrapper);
    }

}
