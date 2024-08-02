package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.File;
import com.example.backend.service.FileService;
import com.example.backend.service.FileUploadService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static com.example.backend.model.enums.FileTypeEnum.getTypeByExtension;

@RequestMapping("/api/student/upload")
@RestController("StudentUploadController")
public class UploadController extends BaseApiController {
    private final FileService fileService;
    private final FileUploadService fileUpload;
    private final UserService userService;

    @Autowired
    public UploadController(FileService fileService, FileUploadService fileUpload, UserService userService) {
        this.fileService = fileService;
        this.fileUpload = fileUpload;
        this.userService = userService;
    }

    @RequestMapping("/avatar/image")
    @ResponseBody
    public RestResponse avatarUploadImg(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        long attachSize = multipartFile.getSize();
        String imgName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUpload.uploadFile(inputStream, attachSize, imgName);
            userService.changePicture(getCurrentUser(), filePath);
            return RestResponse.ok(filePath);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
    }

    @RequestMapping("/file")
    @ResponseBody
    public RestResponse uploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        long attachSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUpload.uploadFile(inputStream, attachSize, fileName);
            //Todo 将上传的文件相关信息存储到数据库
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            com.example.backend.model.entity.File file = new com.example.backend.model.entity.File();
            // Todo name和description可以为空
            file.setName(request.getParameter("name"));
            file.setExtension(fileSuffix);
            file.setType(getTypeByExtension(fileSuffix).toString());
            file.setFilePath(filePath);
            file.setCreateTime(new Date());
            file.setDeleted(false);
            file.setStatus(1);
            file.setDescription(request.getParameter("description"));
            file.setClassId(Integer.valueOf(request.getParameter("classId")));
            file.setDeleted(false);
            file.setUserId(getCurrentUser().getId());
            file.setIsAiGen(false);
            fileService.insertByFilter(file);

            return RestResponse.ok(filePath);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
    }

}
