package com.pyb.edu.controller;


import com.pyb.edu.entity.subject.LevelOne;
import com.pyb.edu.service.SubjectService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-28
 */
@Api(tags = "课程分类管理接口")
@RestController
@RequestMapping("/edu/subject")
public class SubjectController_edu {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("读取Excel数据")
    @PostMapping("add")
    public Result add(MultipartFile file) {
        subjectService.addFile(file, subjectService);
        return Result.ok();
    }

    @ApiOperation("读取一级分类和二级分类，返回层级结构")
    @GetMapping("findAll")
    public Result findAll() {
        List<LevelOne> allSubjects = subjectService.findAllSubjects();
        return Result.ok().data("allSubjects",allSubjects);
    }
}


