package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.request.student.spark.aiimg.AIImgRequest;
import com.example.backend.service.SparkImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/student/spark/img")
@RestController("StudentSparkImgController")
public class SparkImgController extends BaseApiController {
    private final SparkImgService sparkImgService;

    @Autowired
    public SparkImgController(SparkImgService sparkImgService) {
        this.sparkImgService = sparkImgService;
    }

    @PostMapping("/generate")
    public RestResponse<String> generate(@RequestBody @Valid AIImgRequest request){
        String imgUrl = sparkImgService.sparkImgGenerate(request);

        return RestResponse.ok(imgUrl);
    }

}
