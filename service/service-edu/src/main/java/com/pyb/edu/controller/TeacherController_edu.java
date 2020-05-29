package com.pyb.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Teacher;
import com.pyb.edu.entity.vo.TeacherVo;
import com.pyb.edu.service.TeacherService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-21
 */
@Api(tags = "讲师接口")/*swagger页面测试显示*/
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController_edu {

    @Autowired
    private TeacherService teacherService;

    /*rest风格：查询用getmapping，修改用putmapping，删除用deletemapping，添加用postmapping*/
    @ApiOperation("讲师列表")/*swagger页面测试显示*/
    @GetMapping("/getAll")
    public Result findAll() {
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("AllTeachers",list);
    }

    @ApiOperation("分页展示")
    @GetMapping("/getPage/{page}/{size}")
    public Result findPage(@PathVariable(name = "page") int page,
                                 @PathVariable(name = "size") int size) {
        /*page代表第几页，size代表一页多少条数据*/
        Page<Teacher> pageTeacher = new Page<>(page,size);
        teacherService.page(pageTeacher, null);
        /*在这里获取分页后的数据*/

        /*当前的页数*/
        long currentPage = pageTeacher.getCurrent();
        /*当前的全部数据条数*/
        long total = pageTeacher.getTotal();
        /*当前设置的每一页的条数*/
        long sizes = pageTeacher.getSize();
        /*当前总共有几页*/
        long pages = pageTeacher.getPages();

        /*查到的符合条件（null）的全部老师*/
        List<Teacher> teachers = pageTeacher.getRecords();

        if (teachers.size() != 0){
            return Result.ok().data("pageListTeachers",teachers).data("total",total);
        }else {
            return Result.error().data("emptyData",teachers);
        }

    }

    @ApiOperation("条件查询分页展示")
    @PostMapping("/getPageCondition/{page}/{size}")
    public Result findPageCondition(@PathVariable(name = "page") int page,
                                    @PathVariable(name = "size") int size,
                                    @RequestBody(required = false) TeacherVo teacherVo) {
        /*RequestBody:代表用json数据封装进对象，提交json格式的参数，一定要是post方式提交*/

        /*page代表第几页，size代表一页多少条数据*/
        Page<Teacher> pageTeacher = new Page<>(page,size);
        /*创建条件对象*/
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        /*设置查询条件*/
        /*先判断条件是否为空*/
        String name = teacherVo.getName();
        Integer level = teacherVo.getLevel();
        String begin = teacherVo.getBegin();
        String end = teacherVo.getEnd();

        if (!StringUtils.isEmpty(name)){
            /*模糊查询。第一个参数为column代表：表中的对应字段，第二个为传入的模糊查询的参数（%name%）*/
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            /*ge:greater than and equal:大于等于
            * gt:greater than :大于
            * le:less ... :小于等于
            * lt:less than :小于 */

            wrapper.ge("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.le("gmt_modified",end);
        }
        /*根据修改时间排序*/
        wrapper.orderByDesc("gmt_modified");

        teacherService.page(pageTeacher, wrapper);
        /*在这里获取分页后的数据*/


        /*当前的全部数据条数*/
        long total = pageTeacher.getTotal();

        /*查到的符合条件的全部老师*/
        List<Teacher> teachers = pageTeacher.getRecords();

        if (teachers.size() != 0){
            return Result.ok().data("pageListTeachers",teachers).data("total",total);
        }else {
            return Result.error().data("emptyData",teachers);
        }

    }

    @ApiOperation("删除讲师")/*swagger页面测试显示*/
    @DeleteMapping("/{id}")
    public Result del(@ApiParam(name = "id",value = "讲师id", required = true) @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b){
            return Result.ok().data("result",b);
        }else {
            return Result.error().data("result",b);
        }

    }


    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher) {
        boolean b = teacherService.save(teacher);
        if (b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据id查询")
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable String id){
        Teacher byId = teacherService.getById(id);
        if (byId == null){
            return Result.error();
        }else {
            return Result.ok().data("teacher",byId);
        }
    }
    @ApiOperation("修改讲师信息")
    @PutMapping("update/{id}")/*put提交需要传入id值,可以直接用post传入对象方式提交*/
    public Result updateInfo(@PathVariable String id, @RequestBody Teacher teacher) {
        teacher.setId(id);
        boolean b = teacherService.updateById(teacher);
        if (!b){
            return Result.error();
        }else {
            return Result.ok();
        }
    }
    /*@ApiOperation("修改讲师信息")
    @PostMapping("update")*//*put提交需要传入id值，可以直接用post传入对象*//*
    public Result updateInfo(@RequestBody Teacher teacher) {
        boolean b = teacherService.updateById(teacher);
        if (b){
            return Result.error();
        }else {
            return Result.ok();
        }
    }*/



}

