package com.example.backend.service.impl;

import com.example.backend.model.entity.Student;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-02 11:20:19
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
