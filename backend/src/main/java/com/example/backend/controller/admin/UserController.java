package com.example.backend.controller.admin;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.RoleEnum;
import com.example.backend.model.enums.UserStatusEnum;
import com.example.backend.model.request.admin.user.UserAddRequest;
import com.example.backend.model.request.admin.user.UserEditRequest;
import com.example.backend.model.request.user.UserPageRequest;
import com.example.backend.model.request.user.UserRegisterRequest;
import com.example.backend.model.request.user.UserResponse;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.UserService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
public class UserController extends BaseApiController {
    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/addUser")
    public RestResponse addUser(@RequestBody @Valid UserAddRequest request){
        User existUser = userService.getUserByUserName(request.getUserName());
        if (null != existUser) {
            return new RestResponse<>(2, "用户已存在");
        }
        User user = modelMapper.map(request,User.class);
        String encodePwd = authenticationService.pwdEncode(request.getPassword());
        user.setUserUuid(UUID.randomUUID().toString());
        user.setPassword(encodePwd);
        user.setRole(request.getRole());
        user.setStatus(UserStatusEnum.Enable.getCode());
        user.setLastActiveTime(new Date());
        user.setCreateTime(new Date());
        user.setDeleted(false);
        userService.insertByFilter(user);
        return RestResponse.ok();
    }

    @PutMapping("/editUser")
    public RestResponse editUser(@RequestBody @Valid UserAddRequest request){
        User user = modelMapper.map(request,User.class);
        String encodePwd = authenticationService.pwdEncode(request.getPassword());
        user.setPassword(encodePwd);
        user.setModifyTime(new Date());
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

    @PostMapping("/page")
    public RestResponse<PageInfo<UserResponse>> page(@RequestBody UserPageRequest request){
        PageInfo<User> pageInfo = userService.page(request);
        PageInfo<UserResponse> page = PageInfoHelper.copyMap(pageInfo,e->{
            UserResponse response = modelMapper.map(e,UserResponse.class);
            response.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            response.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return response;
        });

        return RestResponse.ok(page);
    }
}
