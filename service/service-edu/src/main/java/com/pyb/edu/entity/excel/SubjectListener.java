package com.pyb.edu.entity.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.edu.entity.Subject;
import com.pyb.edu.service.SubjectService;
import java.util.Map;

/*监听器，把读取的excel内容存到数据库中*/
public class SubjectListener extends AnalysisEventListener<SubjectExcel> {

    SubjectService subjectService;

    /*构造器:加入subjectService方便操作数据库，监听器，拦截器，过滤器等不能被spring管理*/
    public SubjectListener() {
    }

    public SubjectListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectExcel subject, AnalysisContext analysisContext) {
        System.out.println(subject);
        /*读取数据到数据库*/
        if (subject == null){
            return;
        }
        /*判断一级分类是否存在*/
        Subject one = isRepeatedOne(subjectService, subject.getSubjectOne());
        if (one == null){
            one = new Subject();
            one.setTitle(subject.getSubjectOne());
            one.setParentId("0");
            subjectService.save(one);
        }
        /*判断二级分类是否存在*/
        String pid = one.getId();
        Subject two = isRepeatedTwo(subjectService, subject.getSubjectTwo(), pid);
        if (two == null){
            two = new Subject();
            two.setParentId(pid);
            two.setTitle(subject.getSubjectTwo());
            subjectService.save(two);
        }


    }

    /*添加前要判断分类是否重复，防止重复添加*/
    /*一级分类是否重复*/
    public Subject isRepeatedOne(SubjectService subjectService , String name){

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject one = subjectService.getOne(wrapper);
        return one;
    }
    /*二级分类是否重复*/
    public Subject isRepeatedTwo(SubjectService subjectService , String name,String pid){

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println("----"+headMap);
    }
}
