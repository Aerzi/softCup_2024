package com.example.backend.service;

import com.example.backend.common.Result;
import com.example.backend.model.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.StudentRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-02 11:20:19
 */
public interface StudentService extends IService<Student> {
    public boolean existStudent(String username);
    public Result addStudent(StudentRequest studentRequest);

    public Student getStudentInfo(StudentRequest studentRequest);

    public boolean verityPassword(String username,String password);

    public Result loginStatus(StudentRequest studentRequest);

}
