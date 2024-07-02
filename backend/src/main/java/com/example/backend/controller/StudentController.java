package com.example.backend.controller;

import com.example.backend.common.Result;
import com.example.backend.model.entity.Student;
import com.example.backend.model.request.StudentRequest;
import com.example.backend.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-02 11:20:19
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * Todo 前台页面调用  学生账号注册
     * 学生账号注册
     */
    @PostMapping("/register")
    public Result addStudent(@RequestBody StudentRequest studentRequest){
        return studentService.addStudent(studentRequest);
    }

    /**
     * Todo 前台页面调用  学生账号登录
     * 学生账号登录
     */
    @GetMapping("/login/status")
    public Result loginStatus(@RequestBody StudentRequest studentRequest){
        return studentService.loginStatus(studentRequest);
    }



}
