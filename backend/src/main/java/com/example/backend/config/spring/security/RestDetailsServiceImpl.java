package com.example.backend.config.spring.security;

import com.example.backend.model.enums.RoleEnum;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @version 1.0
 * @description: 验证通过之后,第二、三...请求，会调用此类
 * @author feixia0g
 * @date 2021/12/25 9:45
 */
@Service
public class RestDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public RestDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.backend.model.entity.User user = userService.getUserByUserName(username);

        if(user!=null){
            throw new UsernameNotFoundException("Username  not found.");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));

        return null;
    }
}
