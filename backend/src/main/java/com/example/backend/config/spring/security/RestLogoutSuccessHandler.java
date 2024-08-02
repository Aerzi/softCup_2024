package com.example.backend.config.spring.security;

import com.example.backend.base.EventLogMessage;
import com.example.backend.base.SystemCode;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.User;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author feixia0g
 * @version 1.0.0
 * @description: 用户登出
 * @date 2024/7/8 21:00
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;

    public RestLogoutSuccessHandler(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        if (null != springUser) {
            User user = userService.getUserByUserName(springUser.getUsername());
            UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), new Date());
            userEventLog.setContent(user.getUserName() + EventLogMessage.LOGOUT);
            eventPublisher.publishEvent(new UserEvent(userEventLog));
        }
        RestUtil.response(response, SystemCode.OK);
    }
}
