package com.example.backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.mapper.FileMapper;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.student.file.FilePageRequest;
import com.example.backend.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-10 03:57:00
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    private final FileMapper fileMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public void insertByFilter(File file) {
        fileMapper.insert(file);
    }

    @Override
    public PageInfo<File> getAllFileByType(FilePageRequest request) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("deleted", 0);
        fileQueryWrapper.eq("type", request.getType());
        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                fileMapper.selectList(fileQueryWrapper)
        );
    }

    @Override
    public PageInfo<File> getAllValidFileByType(FilePageRequest request) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("deleted", 0);
        fileQueryWrapper.eq("status", 1);
        fileQueryWrapper.eq("type", request.getType());
        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                fileMapper.selectList(fileQueryWrapper)
        );
    }
}
