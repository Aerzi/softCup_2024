package com.example.backend.controller.student;

import com.alibaba.fastjson.JSON;
import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.aippt.*;
import com.example.backend.service.AIPPTService;
import com.example.backend.service.FileUploadService;
import com.example.backend.websocket.AIPPTProgressWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/api/student/spark/ppt")
@RestController("StudentSparkPPTController")
public class AIPPTController extends BaseApiController {
    private final AIPPTService aipptService;
    private final FileUploadService fileUploadService;

    @Autowired
    public AIPPTController(AIPPTService aipptService, FileUploadService fileUploadService) {
        this.aipptService = aipptService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/themeList")
    public RestResponse<AIPPTTemplate> themeList() {
        AIPPTTemplate aipptTemplate = aipptService.getTemplateList();

        return RestResponse.ok(aipptTemplate);
    }

    @PostMapping("/outline")
    public RestResponse<AIPPTOutlineResponse> outline(@RequestBody @Valid AIPPTOutlineRequest request) {
        AIPPTOutlineResponse aipptOutlineResponse = aipptService.outline(request);
        return RestResponse.ok(aipptOutlineResponse);
    }

    @PostMapping("/doc/outline")
    public RestResponse<AIPPTOutlineResponse> outlineByDoc(@RequestParam("file") MultipartFile multipartFile, @RequestParam("data") String jsonString) {
        AIPPTOutlineByDocRequest request = new AIPPTOutlineByDocRequest();
        if (jsonString == null) {
            request = JSON.parseObject(JSON.parse(jsonString).toString(), AIPPTOutlineByDocRequest.class);
        }

        long attachSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String url = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUploadService.uploadFile(inputStream, attachSize, fileName);
            url = filePath;
            System.out.println(url);
            System.out.println(fileName);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }

        AIPPTOutlineResponse aipptOutlineResponse = aipptService.outlineByDoc(request, url, fileName);
        return RestResponse.ok(aipptOutlineResponse);
    }

    @PostMapping("/doc/ppt")
    public RestResponse<AIPPTResponse> pptByDoc(@RequestParam("file") MultipartFile multipartFile, @RequestParam("data") String jsonString) {
        AIPPTByDocRequest request = new AIPPTByDocRequest();
        if (jsonString == null) {
            request = JSON.parseObject(JSON.parse(jsonString).toString(), AIPPTByDocRequest.class);
        }

        long attachSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String url = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUploadService.uploadFile(inputStream, attachSize, fileName);
            url = filePath;
            System.out.println(url);
            System.out.println(fileName);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
        AIPPTResponse aipptResponse = aipptService.pptByDoc(request, url, fileName);

        return RestResponse.ok(aipptResponse);
    }

    @GetMapping("/ppt/progress/{sid}")
    public RestResponse checkProgress(@PathVariable String sid) {
        int progress = 0;
        AIPPTProgressResult result;
        while (progress < 100) {
            result = aipptService.checkProgress(sid);
            progress = result.getData().getProcess();

            String jsonString = JSON.toJSONString(result);
            try {
                AIPPTProgressWebSocketServer.sendInfo(jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (progress < 100) {
                try {
                    Thread.sleep(5000); // 暂停2秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return RestResponse.ok();
    }

}