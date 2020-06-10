package com.pyb.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.cms.entity.CrmBanner;
import com.pyb.cms.service.CrmBannerService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台的banner表 后台的crud接口
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/cms/banner/admin")
//@CrossOrigin 和gateway里面处理二选一，不能处理二次
@Api(tags = "banner的后台管理员接口")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit) {

        Page<CrmBanner> pageB = bannerService.pageBanner(page,limit);
        List<CrmBanner> pageBanner = pageB.getRecords();
        long total = pageB.getTotal();
        /*返回分页查询的全部页数记录条数数total和查到的list集合*/
        return Result.ok().data("pageBanner",pageBanner).data("total",total);
    }

    @ApiOperation("根据id查询banner")
    @GetMapping("getBanner/{id}")
    public Result getBanner(@PathVariable("id") String id) {

        CrmBanner banner = bannerService.getById(id);
        if (banner!=null){
            return Result.ok().data("banner",banner);
        }
        return Result.error().message("该banner不存在");
    }

    @ApiOperation("增加banner")
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner banner) {
        boolean save = bannerService.save(banner);
        return save? Result.ok():Result.error();
    }

    @ApiOperation("修改banner")
    @PutMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        boolean b = bannerService.updateById(banner);
        return b? Result.ok():Result.error();
    }

    @ApiOperation("删除banner")
    @DeleteMapping("delBanner/{bannerId}")
    public Result delBanner(@PathVariable String bannerId) {
        boolean b = bannerService.removeById(bannerId);
        return b? Result.ok():Result.error();
    }


}

