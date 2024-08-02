package com.example.backend.controller.admin;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.request.admin.eventlog.UserEventLogEditRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogPageRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogResponse;
import com.example.backend.service.UserEventLogService;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-08-02 02:49:01
 */
@RestController("AdminUserEventLogController")
@RequestMapping(value = "/api/admin/event/log")
public class UserEventLogController extends BaseApiController {
    private final UserEventLogService userEventLogService;

    @Autowired
    public UserEventLogController(UserEventLogService userEventLogService) {
        this.userEventLogService = userEventLogService;
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<UserEventLogResponse>> page(@RequestBody UserEventLogPageRequest request) {
        PageInfo<UserEventLog> pageInfo = userEventLogService.page(request);
        PageInfo<UserEventLogResponse> page = PageInfoHelper.copyMap(pageInfo, e -> modelMapper.map(e, UserEventLogResponse.class));
        return RestResponse.ok(page);
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody UserEventLogEditRequest request){
        UserEventLog userEventLog = modelMapper.map(request,UserEventLog.class);
        userEventLogService.updateByIdFilter(userEventLog);
        return RestResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        userEventLogService.deleteByFilterId(id);
        return RestResponse.ok();
    }
}
