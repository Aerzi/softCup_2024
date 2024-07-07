package com.example.backend.context;

import com.example.backend.model.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @version 1.0
 * @description The enum System code.
 * @author feixia0g
 * @date 2024/7/7 10:40
 */
@Component
public class WebContext {
    private static final String USER_ATTRIBUTES = "USER_ATTRIBUTES";
    private final UserService userService;

    /**
     * Instantiates a new Web context.
     *
     * @param userService the user service
     */
    @Autowired
    public WebContext(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets current user.
     *
     * @param user the user
     */
    public void setCurrentUser(User user) {
        RequestContextHolder.currentRequestAttributes().setAttribute(USER_ATTRIBUTES, user, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute(USER_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
        if (null != user) {
            return user;
        } else {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (null == springUser) {
                return null;
            }
            user = userService.getUserByUserName(springUser.getUsername());
            if (null != user) {
                setCurrentUser(user);
            }
            return user;
        }
    }

}
