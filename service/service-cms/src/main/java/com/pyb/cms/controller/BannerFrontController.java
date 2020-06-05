package com.pyb.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.cms.entity.CrmBanner;
import com.pyb.cms.service.CrmBannerService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/cms/banner/front")
@CrossOrigin
@Api(tags = "banner的前台客户展示接口")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAll")
    @ApiOperation("排序查询前3条banner")
    public Result getAll() {

        List<CrmBanner> list = bannerService.getlist();
        if (list.size() > 0){
            return Result.ok().data("list",list);
        }
        return Result.error();
    }


}

