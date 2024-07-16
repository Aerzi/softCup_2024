package com.example.backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.backend.mapper.FileMapper;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.file.FilePageRequest;
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
    public File selectById(Integer id) {
        return fileMapper.selectById(id);
    }

    @Override
    public List<File> allFile() {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        return fileMapper.selectList(fileQueryWrapper);
    }

    @Override
    public PageInfo<File> page(FilePageRequest request) {
        LambdaQueryWrapper<File> fileQueryWrapper = new LambdaQueryWrapper<>();
        fileQueryWrapper.eq(request.getId()!=null, File::getId,request.getId())
                .eq(request.getName()!=null,File::getName,request.getName())
                .eq(request.getExtension()!=null,File::getExtension,request.getExtension())
                .eq(request.getType()!=null,File::getType,request.getType())
                .eq(request.getStatus()!=null,File::getStatus,request.getStatus())
                .eq(request.getDescription()!=null,File::getDescription,request.getDescription())
                .eq(request.getUserId()!=null,File::getUserId,request.getUserId())
                .eq(File::getDeleted,0);

        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                fileMapper.selectList(fileQueryWrapper)
        );
    }

    @Override
    public void updateByIdFilter(File file) {
        fileMapper.updateById(file);
    }

    @Override
    public void deleteByIdFilter(Integer id) {
        LambdaUpdateWrapper<File> fileQueryWrapper = new LambdaUpdateWrapper<>();
        fileQueryWrapper.eq(File::getId,id);
        fileQueryWrapper.set(File::getDeleted,1);
        fileMapper.update(null,fileQueryWrapper);
    }
}
