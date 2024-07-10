package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @PostMapping("/page")
//    public RestResponse pageList(){
//
//    }


}
