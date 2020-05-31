package com.pyb.edu.service;

import com.pyb.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.edu.entity.subject.LevelOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-28
 */
public interface SubjectService extends IService<Subject> {

    void addFile(MultipartFile file, SubjectService subjectService);

    List<LevelOne> findAllSubjects();
}
