package com.pyb.edu.controller;


import com.pyb.edu.entity.chapter.ChapterOne;
import com.pyb.edu.service.ChapterService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Api(tags = "章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("findAll/{cid}")
    @ApiOperation("读取某一个课程的章节，返回层级结构")
    public Result findAll(@PathVariable String cid) {
        List<ChapterOne> list = chapterService.findChapterAndVideo(cid);
        return Result.ok().data("list",list);
    }

}

