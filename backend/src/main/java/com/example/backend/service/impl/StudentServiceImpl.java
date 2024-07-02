package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.Result;
import com.example.backend.model.entity.Student;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.model.request.StudentRequest;
import com.example.backend.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        if(this.existStudent(studentRequest.getName())){
            return Result.warning("该用户已被注册！");
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentRequest,student);

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
}
