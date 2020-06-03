package com.pyb.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.cms.entity.CrmBanner;
import com.pyb.cms.mapper.CrmBannerMapper;
import com.pyb.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public Page<CrmBanner> pageBanner(Integer page, Integer limit) {
        Page<CrmBanner> page1 = new Page<>(page,limit);
        Page<CrmBanner> page2 = (Page<CrmBanner>) this.page(page1, null);
        return page2;
    }
}
