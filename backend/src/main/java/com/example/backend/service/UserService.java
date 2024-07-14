package com.example.backend.service;

import com.example.backend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.user.UserEditRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-07 09:32:16
 */
public interface UserService extends IService<User> {
    /**
     * getUserByUserName
     *
     * @param username username
     * @return User
     */
    public User getUserByUserName(String username);

    public void changePicture(User user, String imagePath);

    public void insertByFilter(User user);

    public void edit(User request);
}
