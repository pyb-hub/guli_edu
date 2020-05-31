package com.pyb.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.edu.entity.Subject;
import com.pyb.edu.entity.excel.SubjectExcel;
import com.pyb.edu.entity.excel.SubjectListener;
import com.pyb.edu.entity.subject.LevelOne;
import com.pyb.edu.entity.subject.LevelTwo;
import com.pyb.edu.mapper.SubjectMapper;
import com.pyb.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-28
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void addFile(MultipartFile file, SubjectService subjectService) {

        InputStream in = null;
        try {
            in = file.getInputStream();
            EasyExcel.read(in,SubjectExcel.class,new SubjectListener(subjectService)).sheet("Sheet1").doRead();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public List<LevelOne> findAllSubjects() {
        /*获取一级分类*/
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<Subject> subjectOne = baseMapper.selectList(wrapper);
        wrapper = new QueryWrapper<>();
        /*获取二级分类*/
        wrapper.ne("parent_id","0");
        List<Subject> subjectTwo = baseMapper.selectList(wrapper);

        /*封装数据*/
        List<LevelOne> list = new ArrayList<>();
        /*遍历所有的分类*/
        for (Subject subject:subjectOne) {
            /*设置一级分类内容*/
            LevelOne levelOne = new LevelOne();
            String id = subject.getId();
            String title = subject.getTitle();
            levelOne.setId(id);
            levelOne.setTitle(title);
            //BeanUtils.copyProperties(subject,levelOne);

            /*设置二级分类*/
            List<LevelTwo> list2 = new ArrayList<>();
            for (Subject subject2:subjectTwo) {
                if (subject2.getParentId().equals(id)){
                    LevelTwo levelTwo = new LevelTwo();
                    /*levelTwo.setId(subject2.getId());
                    levelTwo.setTitle(subject2.getTitle());*/
                    //简化代码：把第一个参数的值复制给第二个参数，注意：复制他们都的定义的属性值；
                    BeanUtils.copyProperties(subject2,levelTwo);
                    list2.add(levelTwo);
                }
            }

            levelOne.setChildren(list2);
            list.add(levelOne);
        }
        return list;

    }
}
