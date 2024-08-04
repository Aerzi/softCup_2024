package com.example.backend.service;

import com.example.backend.model.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.student.aclass.ClassResponse;
import com.example.backend.model.request.teacher.aclass.ClassPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-14 10:50:20
 */
public interface ClassService extends IService<Class> {
    public void insertByFilter(Class aclass);
    public Class select(Integer id);
    public List<ClassResponse> listClass(Integer studentId);
    public PageInfo<Class> page(ClassPageRequest request);
    public PageInfo<ClassResponse> page(com.example.backend.model.request.student.aclass.ClassPageRequest request, Integer studentId);
    public void updateByIdFilter(Class request);
    public void deleteByIdFilter(Integer id);
}
