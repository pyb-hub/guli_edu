package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.edu.entity.Teacher;
import com.pyb.edu.mapper.TeacherMapper;
import com.pyb.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    @Cacheable(cacheNames = "teachers",key = "'fontIndex'")
    public List<Teacher> teacherIndex() {
        QueryWrapper<Teacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("level");
        wrapper2.last("limit 4");
        return this.list(wrapper2);
    }
}
