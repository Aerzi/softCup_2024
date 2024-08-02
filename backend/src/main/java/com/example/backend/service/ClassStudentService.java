package com.example.backend.service;

import com.example.backend.model.entity.ClassStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.student.aclass.ClassStudentResponse;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-14 11:03:56
 */
public interface ClassStudentService extends IService<ClassStudent> {
    public void insertByFilter(ClassStudent classStudent);
    public void batchInsertByFilter(List<ClassStudent> requests);
    public PageInfo<User> page(ClassStudentPageRequest request);
    public void deleteByUserId(Integer userId);
    public void deleteByClassId(Integer classId);
    public void deleteByClassIdAndUserId(ClassStudent request);
    public ClassStudentResponse atClass(Integer userId);
}
