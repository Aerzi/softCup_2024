package com.example.backend.service.impl;

import com.example.backend.model.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-07 09:13:23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
