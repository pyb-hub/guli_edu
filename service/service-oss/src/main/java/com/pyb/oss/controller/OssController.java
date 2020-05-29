package com.pyb.oss.controller;


import com.pyb.oss.Service.OssService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/oss/file")
@CrossOrigin/*解决跨域问题*/
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("avatar")
    public Result fileUpload(MultipartFile file) {

        /*返回上传的路径*/
        String url = ossService.upload(file);
        return Result.ok().data("url",url);
    }
}
