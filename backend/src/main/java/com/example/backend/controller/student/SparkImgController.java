package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.File;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.request.student.spark.aiimg.AIImgPageRequest;
import com.example.backend.model.request.student.spark.aiimg.AIImgRequest;
import com.example.backend.model.request.student.spark.aiimg.AIImgResponse;
import com.example.backend.model.request.student.spark.aiimg.AIImgSaveRequest;
import com.example.backend.service.FileService;
import com.example.backend.service.SparkImgService;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

import static com.example.backend.model.enums.FileTypeEnum.getTypeByExtension;

@RequestMapping("/api/student/spark/img")
@RestController("StudentSparkImgController")
public class SparkImgController extends BaseApiController {
    private final SparkImgService sparkImgService;
    private final FileService fileService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public SparkImgController(SparkImgService sparkImgService, FileService fileService, ApplicationEventPublisher eventPublisher) {
        this.sparkImgService = sparkImgService;
        this.fileService = fileService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/generate")
    public RestResponse<String> generate(@RequestBody @Valid AIImgRequest request) {
        String imgUrl = sparkImgService.sparkImgGenerate(request);

        //根据文档生成大纲 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.AI_IMG);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(imgUrl);
    }

    @PostMapping("/save")
    public RestResponse save(@RequestBody @Valid AIImgSaveRequest request) {
        com.example.backend.model.entity.File file = new com.example.backend.model.entity.File();
        // Todo name和description可以为空
        file.setName(request.getName());
        file.setExtension("png");
        file.setType(getTypeByExtension("png").toString());
        file.setFilePath(request.getFilePath());
        file.setCreateTime(new Date());
        file.setDeleted(false);
        file.setStatus(1);
        if (request.getDescription() != null) {
            file.setDescription(request.getDescription());
        } else {
            file.setDescription(null);
        }
        file.setClassId(null);
        file.setDeleted(false);
        file.setUserId(getCurrentUser().getId());
        file.setIsAiGen(true);
        fileService.insertByFilter(file);

        return RestResponse.ok();
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<AIImgResponse>> page(@RequestBody @Valid AIImgPageRequest request){
        PageInfo<File> pageInfo = sparkImgService.page(request);
        PageInfo<AIImgResponse> page = PageInfoHelper.copyMap(pageInfo,e-> modelMapper.map(e,AIImgResponse.class));
        return RestResponse.ok(page);
    }

}
