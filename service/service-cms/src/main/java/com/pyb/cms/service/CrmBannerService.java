package com.pyb.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-03
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Page<CrmBanner> pageBanner(Integer page, Integer limit);
}
