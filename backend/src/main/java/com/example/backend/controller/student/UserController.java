package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.User;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.enums.RoleEnum;
import com.example.backend.model.enums.UserStatusEnum;
import com.example.backend.model.request.user.UserEditRequest;
import com.example.backend.model.request.user.UserRegisterRequest;
import com.example.backend.model.request.user.UserResponse;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.UserService;
import com.example.backend.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController("StudentUserController")
@RequestMapping(value = "/api/student/user")
public class UserController extends BaseApiController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, ApplicationEventPublisher eventPublisher, AuthenticationService authenticationService){
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public RestResponse register(@RequestBody @Valid UserRegisterRequest request){
        User existUser = userService.getUserByUserName(request.getUserName());
        if (null != existUser) {
            return new RestResponse<>(2, "用户已存在");
        }
        User user = modelMapper.map(request,User.class);
        String encodePwd = authenticationService.pwdEncode(request.getPassword());
        user.setUserUuid(UUID.randomUUID().toString());
        user.setPassword(encodePwd);
        user.setRole(RoleEnum.STUDENT.getCode());
        user.setStatus(UserStatusEnum.Enable.getCode());
        user.setLastActiveTime(new Date());
        user.setCreateTime(new Date());
        user.setDeleted(false);
        userService.insertByFilter(user);

        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(user.getId());
        userEventLog.setUserName(user.getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(EventLogMessage.WELCOME + user.getUserName() + EventLogMessage.REGISTER);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok();
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid UserEditRequest request){
        User user = modelMapper.map(request,User.class);
        String encodePwd = authenticationService.pwdEncode(request.getPassword());
        user.setPassword(encodePwd);
        user.setModifyTime(new Date());
        user.setId(getCurrentUser().getId());
        userService.edit(user);
        return RestResponse.ok();
    }

    @GetMapping("/info")
    public RestResponse<UserResponse> getCurrentInfo(){
        User user = userService.getCurrentUserInfo(getCurrentUser().getId());
        UserResponse response = modelMapper.map(user,UserResponse.class);
        response.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
        response.setModifyTime(DateTimeUtil.dateFormat(user.getModifyTime()));
        response.setLastActiveTime(DateTimeUtil.dateFormat(user.getLastActiveTime()));
        return RestResponse.ok(response);
    }
}
