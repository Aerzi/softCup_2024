package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.student.file.FilePageRequest;
import com.example.backend.model.request.student.file.FileResponse;
import com.example.backend.service.FileService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/student/file")
@RestController("StudentFileController")
public class FileController extends BaseApiController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/page")
    public RestResponse pageList(@RequestBody FilePageRequest request){
        PageInfo<File> pageInfo = fileService.getAllFileByType(request);
        PageInfo<FileResponse> page = PageInfoHelper.copyMap(pageInfo,q->{
            FileResponse res = modelMapper.map(q,FileResponse.class);
            res.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            return res;
        });
        return RestResponse.ok(page);
    }

}
