package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.RoleEnum;
import com.example.backend.model.enums.UserStatusEnum;
import com.example.backend.model.request.student.user.UserLoginRequest;
import com.example.backend.model.request.student.user.UserRegisterRequest;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController("StudentUserController")
@RequestMapping(value = "/api/student/user")
public class UserController extends BaseApiController {
    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService){
        this.userService = userService;
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
        return RestResponse.ok();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public String test(){
        System.out.println("---------->test");
        return "test .... ";
    }
}
