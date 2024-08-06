package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.entity.chatdoc.ChatDocSummaryQueryResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiUrlResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocChatRequest;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocSummaryQueryRequest;
import com.example.backend.service.AIChatDocService;
import com.example.backend.service.FileService;
import com.example.backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;
    private final FileService fileService;

    @Autowired
    public AIChatDocController(AIChatDocService aiChatDocService, FileUploadService fileUploadService, ApplicationEventPublisher eventPublisher, FileService fileService) {
        this.aiChatDocService = aiChatDocService;
        this.fileUploadService = fileUploadService;
        this.eventPublisher = eventPublisher;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public RestResponse<ChatDocApiUrlResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
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
            file.setIsAiGen(false);
            fileService.insertByFilter(file);

            //上传问答文档 日志记录
            UserEventLog userEventLog = new UserEventLog();

            userEventLog.setCreateTime(new Date());
            userEventLog.setDeleted(false);
            userEventLog.setUserId(getCurrentUser().getId());
            userEventLog.setUserName(getCurrentUser().getUserName());
            userEventLog.setCreateTime(new Date());
            userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.CHAT_DOC_UPLOAD);
            eventPublisher.publishEvent(new UserEvent(userEventLog));

        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
        ChatDocApiResponse chatDocApiResponse = aiChatDocService.upload(url,fileName);
        System.out.println(chatDocApiResponse.getSid());
        System.out.println(chatDocApiResponse.getData().getFileId());

        ChatDocApiUrlResponse urlResponse = new ChatDocApiUrlResponse();
        urlResponse.setResponse(chatDocApiResponse);
        urlResponse.setUrl(url);
        // 查找最后一个点的位置
        int dotIndex = fileName.lastIndexOf('.');

        // 获取扩展名
        String extension = fileName.substring(dotIndex + 1);
        urlResponse.setType(extension);

        return RestResponse.ok(urlResponse);
    }

    @PostMapping("/chat")
    public RestResponse chat(@RequestBody @Valid ChatDocChatRequest request){
        aiChatDocService.chat(request);

        //文档问答 日志记录
        UserEventLog userEventLog = new UserEventLog();

        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.CHAT_DOC_CHAT);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok();
    }

    @PostMapping("/summary")
    public RestResponse summary(@RequestBody @Valid ChatDocSummaryQueryRequest request){
        ChatDocSummaryQueryResponse response = aiChatDocService.query(request);

        //文档总结 日志记录
        UserEventLog userEventLog = new UserEventLog();

        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.CHAT_DOC_SUMMARY);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(response);
    }
}
