package com.pyb.edu.service;

import com.pyb.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-21
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> teacherIndex();
}
