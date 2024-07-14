package com.example.backend.controller.admin;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.file.FileEditRequest;
import com.example.backend.model.request.file.FilePageRequest;
import com.example.backend.model.request.file.FileResponse;
import com.example.backend.service.FileService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/admin/file")
@RestController("AdminFileController")
public class FileController extends BaseApiController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/select/{id}")
    public RestResponse<FileResponse> selectById(@PathVariable Integer id) {
        File file = fileService.selectById(id);
        FileResponse response = modelMapper.map(file, FileResponse.class);
        response.setCreateTime(DateTimeUtil.dateFormat(file.getCreateTime()));
        response.setModifyTime(DateTimeUtil.dateFormat(file.getModifyTime()));
        return RestResponse.ok(response);
    }

    @GetMapping("/list")
    public RestResponse<List<File>> list() {
        List<File> files = fileService.allFile();
        return RestResponse.ok(files);
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<FileResponse>> pageList(@RequestBody FilePageRequest request) {
        PageInfo<File> pageInfo = fileService.page(request);
        PageInfo<FileResponse> page = PageInfoHelper.copyMap(pageInfo, q -> {
            FileResponse res = modelMapper.map(q, FileResponse.class);
            res.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            res.setModifyTime(DateTimeUtil.dateFormat(q.getModifyTime()));
            return res;
        });
        return RestResponse.ok(page);
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid FileEditRequest request) {
        File file = modelMapper.map(request, File.class);
        file.setModifyTime(new Date());
        fileService.updateByIdFilter(file);
        return RestResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        fileService.deleteByIdFilter(id);
        return RestResponse.ok();
    }
}
