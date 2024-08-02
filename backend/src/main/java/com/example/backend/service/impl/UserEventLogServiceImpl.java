package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.mapper.UserEventLogMapper;
import com.example.backend.model.request.admin.eventlog.UserEventLogEditRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogPageRequest;
import com.example.backend.model.request.admin.eventlog.UserEventLogResponse;
import com.example.backend.service.UserEventLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-08-02 02:49:01
 */
@Service
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogMapper, UserEventLog> implements UserEventLogService {
    private final UserEventLogMapper userEventLogMapper;

    @Autowired
    public UserEventLogServiceImpl(UserEventLogMapper userEventLogMapper) {
        this.userEventLogMapper = userEventLogMapper;
    }

    @Override
    public void insertByFilter(UserEventLog userEventLog) {
        userEventLogMapper.insert(userEventLog);
    }

    @Override
    public PageInfo<UserEventLog> page(UserEventLogPageRequest request) {
        LambdaQueryWrapper<UserEventLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(request.getUserId()!=null,UserEventLog::getUserId,request.getUserId())
                .eq(request.getUserName()!=null,UserEventLog::getUserName,request.getUserName())
                .like(request.getContent()!=null,UserEventLog::getContent,request.getContent())
                .eq(UserEventLog::getDeleted,false);

        return PageHelper.startPage(request.getPageIndex(),request.getPageSize(),"id desc").doSelectPageInfo(()->
                userEventLogMapper.selectList(lambdaQueryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(UserEventLog userEventLog) {
        userEventLogMapper.updateById(userEventLog);
    }

    @Override
    public void deleteByFilterId(Integer id) {
        LambdaUpdateWrapper<UserEventLog> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserEventLog::getId,id)
                .set(UserEventLog::getDeleted,true);
        userEventLogMapper.update(null,updateWrapper);
    }
}
