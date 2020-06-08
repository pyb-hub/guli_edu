package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Teacher;
import com.pyb.edu.mapper.TeacherMapper;
import com.pyb.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    /*@CacheEvict清空缓存，适用于删除方法
    @CachePut增加方法和修改方法，添加缓存*/
    public List<Teacher> teacherIndex() {
        QueryWrapper<Teacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("level");
        wrapper2.last("limit 4");
        return this.list(wrapper2);
    }

    @Override
    public HashMap<String, Object> getPageInfo(Integer currentPage, Integer size) {
        Page<Teacher> page = new Page<>(currentPage,size);
        this.page(page, null);
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();
        long current = page.getCurrent();
        long size1 = page.getSize();
        long pages = page.getPages();
        boolean next = page.hasNext();
        boolean previous = page.hasPrevious();
        HashMap<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size1);
        map.put("next",next);
        map.put("previous",previous);
        map.put("pages",pages);
        return map;
    }
}
