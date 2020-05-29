package com.pyb.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.pyb.edu.entity.Subject;
import com.pyb.edu.entity.excel.SubjectExcel;
import com.pyb.edu.entity.excel.SubjectListener;
import com.pyb.edu.mapper.SubjectMapper;
import com.pyb.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
}
