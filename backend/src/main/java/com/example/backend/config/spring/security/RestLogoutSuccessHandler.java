package com.example.backend.config.spring.security;

import com.example.backend.base.SystemCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0.0
 * @description: 用户登出
 * @author feixia0g
 * @date 2024/7/8 21:00
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        RestUtil.response(response, SystemCode.OK);
    }
}
