package com.example.backend.service;

import com.example.backend.mapper.UserEventLogMapper;
import com.example.backend.model.entity.UserEventLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.request.admin.eventlog.UserEventLogEditRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogPageRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogResponse;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-08-02 02:49:01
 */
public interface UserEventLogService extends IService<UserEventLog> {
    public void insertByFilter(UserEventLog userEventLog);
    public PageInfo<UserEventLog> page(UserEventLogPageRequest request);
    public void updateByIdFilter(UserEventLog userEventLog);
    public void deleteByFilterId(Integer id);
}
