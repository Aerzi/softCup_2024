package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.student.file.FilePageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-10 03:57:00
 */
public interface FileService extends IService<File> {
    public void insertByFilter(File file);
    public File selectById(Integer id);
    public List<File> allFile();
    public PageInfo<File> getAllFileByType(FilePageRequest request);
    public PageInfo<File> getAllValidFileByType(FilePageRequest request);
    public void updateByIdFilter(File file);
    public void deleteByIdFilter(Integer id);
}
