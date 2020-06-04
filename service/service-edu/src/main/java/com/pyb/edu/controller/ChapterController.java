package com.pyb.edu.controller;


import com.pyb.edu.entity.Chapter;
import com.pyb.edu.entity.chapter.ChapterOne;
import com.pyb.edu.service.ChapterService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
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
    @ApiOperation("读取cid的课程的章节，返回层级结构")
    public Result findAll(@PathVariable String cid) {
        List<ChapterOne> list = chapterService.findChapterAndVideo(cid);
        return Result.ok().data("chapterList",list);
    }

    @GetMapping("findOne/{id}")
    @ApiOperation("根据id读取章节")
    public Result findOne(@PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok().data("chapter",chapter);
    }

    @PostMapping("addChapter")
    @ApiOperation("添加章节")
    public Result addOne(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok();
    }

    @PostMapping("updateChapter")
    @ApiOperation("修改章节")
    public Result updateOne(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @DeleteMapping("delChapter/{id}")
    @ApiOperation("通过章节id删除章节")
    public Result delOne(@PathVariable String id) {
        /*删除章节和对应的小节和视频*/
        chapterService.delete(id);
        return Result.ok();
    }
}

