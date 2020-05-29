package com.pyb.edu.controller;


import com.pyb.edu.service.SubjectService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-28
 */
@Api(tags = "课程接口")
@RestController
@RequestMapping("/edu/subject")
public class SubjectController_edu {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("读取数据")
    @PostMapping("add")
    public Result add(MultipartFile file) {
        subjectService.addFile(file, subjectService);

        return Result.ok();
    }

}

