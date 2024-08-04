package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.chatdoc.ChatDocSummaryQueryResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocChatRequest;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocSummaryQueryRequest;
import com.example.backend.service.AIChatDocService;
import com.example.backend.service.FileService;
import com.example.backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static com.example.backend.model.enums.FileTypeEnum.getTypeByExtension;

@RequestMapping("/api/student/spark/chat/doc")
@RestController("StudentSparkChatDocController")
public class AIChatDocController extends BaseApiController {
    private final AIChatDocService aiChatDocService;
    private final FileUploadService fileUploadService;
    private final FileService fileService;

    @Autowired
    public AIChatDocController(AIChatDocService aiChatDocService, FileUploadService fileUploadService, FileService fileService) {
        this.aiChatDocService = aiChatDocService;
        this.fileUploadService = fileUploadService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public RestResponse<ChatDocApiResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        long attachSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String url = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUploadService.uploadFile(inputStream, attachSize, fileName);
            url = filePath;
            System.out.println(url);
            //Todo 将上传的文件相关信息存储到数据库
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            com.example.backend.model.entity.File file = new com.example.backend.model.entity.File();
            // Todo name和description可以为空
            file.setName(fileName);
            file.setExtension(fileSuffix);
            file.setType(getTypeByExtension(fileSuffix).toString());
            file.setFilePath(filePath);
            file.setCreateTime(new Date());
            file.setDeleted(false);
            file.setStatus(1);
            file.setDescription(null);
            file.setClassId(null);
            file.setDeleted(false);
            file.setUserId(getCurrentUser().getId());
            file.setIsAiGen(true);
            fileService.insertByFilter(file);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
        ChatDocApiResponse chatDocApiResponse = aiChatDocService.upload(url,fileName);
        System.out.println(chatDocApiResponse.getSid());
        System.out.println(chatDocApiResponse.getData().getFileId());

        return RestResponse.ok(chatDocApiResponse);
    }

    @PostMapping("/chat")
    public RestResponse chat(@RequestBody @Valid ChatDocChatRequest request){
        aiChatDocService.chat(request);
        return RestResponse.ok();
    }

    @PostMapping("/summary")
    public RestResponse summary(@RequestBody @Valid ChatDocSummaryQueryRequest request){
        ChatDocSummaryQueryResponse response = aiChatDocService.query(request);
        return RestResponse.ok(response);
    }
}
