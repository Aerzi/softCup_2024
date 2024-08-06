package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.model.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.request.user.UserEditRequest;
import com.example.backend.model.request.user.UserPageRequest;
import com.example.backend.model.request.user.UserResponse;
import com.example.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-07 09:32:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        userQueryWrapper.eq("deleted",0);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    @Transactional
    public void changePicture(User user, String imagePath) {
        User changePictureUser = new User();
        changePictureUser.setId(user.getId());
        changePictureUser.setImagePath(imagePath);
        userMapper.updateById(changePictureUser);
    }

    @Override
    public void insertByFilter(User user) {
        userMapper.insert(user);
    }

    @Override
    public void edit(User request) {
        userMapper.updateById(request);
    }

    @Override
    public User getCurrentUserInfo(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public PageInfo<User> page(UserPageRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getDeleted,false)
                .eq(User::getStatus,1);

        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                userMapper.selectList(lambdaQueryWrapper)
        );
    }
}
