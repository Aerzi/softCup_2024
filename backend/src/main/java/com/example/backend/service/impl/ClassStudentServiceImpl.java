package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.mapper.ClassMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.entity.Class;
import com.example.backend.model.entity.ClassStudent;
import com.example.backend.mapper.ClassStudentMapper;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.student.aclass.ClassStudentExitRequest;
import com.example.backend.model.request.student.aclass.ClassStudentResponse;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentPageRequest;
import com.example.backend.model.request.teacher.calssstudent.Student;
import com.example.backend.service.ClassStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * @since 2024-07-14 11:03:56
 */
@Service
public class ClassStudentServiceImpl extends ServiceImpl<ClassStudentMapper, ClassStudent> implements ClassStudentService {
    private final ClassStudentMapper classStudentMapper;
    private final UserMapper userMapper;
    private final ClassMapper classMapper;

    @Autowired
    public ClassStudentServiceImpl(ClassStudentMapper classStudentMapper, UserMapper userMapper, ClassMapper classMapper) {
        this.classStudentMapper = classStudentMapper;
        this.userMapper = userMapper;
        this.classMapper = classMapper;
    }

    @Override
    public void insertByFilter(ClassStudent classStudent) {
        classStudentMapper.insert(classStudent);
    }

    @Override
    public void batchInsertByFilter(List<ClassStudent> requests) {
        if (requests != null && !requests.isEmpty()) {
            for (ClassStudent request : requests) {
                insertByFilter(request);
            }
        }
    }

    @Override
    public PageInfo<Student> page(ClassStudentPageRequest request) {
        LambdaQueryWrapper<ClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(request.getClassId() != null, ClassStudent::getClassId, request.getClassId());

        PageInfo<ClassStudent> pageInfo = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                classStudentMapper.selectList(queryWrapper)
        );

        List<Integer> userIds = pageInfo.getList().stream()
                .map(e -> e.getUserId()
                ).collect(Collectors.toList());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIds);
        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
            userMapper.selectList(userQueryWrapper)
        );
    }

    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        classStudentMapper.delete(queryWrapper);
    }

    @Override
    public void deleteByClassId(Integer classId) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId);
        classStudentMapper.delete(queryWrapper);
    }

    @Override
    public void deleteByClassIdAndUserId(ClassStudentExitRequest request) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",request.getUserId());
        queryWrapper.eq("class_id",request.getClassId());
        classStudentMapper.delete(queryWrapper);
    }

    @Override
    public ClassStudentResponse atClass(Integer userId) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        ClassStudent classStudent= classStudentMapper.selectOne(queryWrapper);

        QueryWrapper<Class> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper.eq("id",classStudent.getClassId());
        ClassStudentResponse response = new ClassStudentResponse();
        response.setAClass(classMapper.selectOne(classQueryWrapper));
        response.setStudentId(userId);
        return response;
    }

}
