package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.Result;
import com.example.backend.model.entity.Student;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.model.request.StudentRequest;
import com.example.backend.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.example.backend.constants.Constants.SALT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-02 11:20:19
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 根据name判断Student是否已被注册
     *
     * @param username
     * @return 返回是否被注册的判断结果
     */
    @Override
    public boolean existStudent(String username) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 添加学生信息
     * @param studentRequest
     * @return Result
     */
    @Override
    public Result addStudent(StudentRequest studentRequest) {
        if (this.existStudent(studentRequest.getName())) {
            return Result.warning("该用户已被注册！");
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentRequest, student);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex((SALT + studentRequest.getPassword()).getBytes(StandardCharsets.UTF_8));
        student.setPassword(password);

        //对数据库进行操作  新增consumer新用户
        try {
            if (studentMapper.insert(student) > 0) {
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败");
            }
        } catch (DataAccessException e) {
            return Result.fatal(e.getMessage());
        }
    }

    @Override
    public Student getStudentInfo(StudentRequest studentRequest) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("name",studentRequest.getName());
        String secretPassword = DigestUtils.md5DigestAsHex((SALT + studentRequest.getPassword()).getBytes(StandardCharsets.UTF_8));
        studentQueryWrapper.eq("password",secretPassword);

        Student student = new Student();
        try{
            student = getOne(studentQueryWrapper);
            if(student == null){
                System.out.println("No user found with given credentials.");
                throw new RuntimeException("User not found or incorrect password");
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch user info", e);
        }
        return null;
    }

    /**
     * 判断学生用户账号密码是否相符
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean verityPassword(String username, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);

        String secretPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        queryWrapper.eq("password", secretPassword);
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 学生用户登录
     * @param studentRequest
     * @return
     */
    @Override
    public Result loginStatus(StudentRequest studentRequest) {
        if (verityPassword(studentRequest.getName(), studentRequest.getPassword())) {
            Student student = new Student();
            BeanUtils.copyProperties(studentRequest,student);

            String token = TokenUtils.genToken(student.getId().toString(),student.getPassword());
            studentRequest.setToken(token);
            return Result.success("登录成功", studentRequest);
        }else{
            return Result.error("用户名或密码错误");
        }
    }
}
