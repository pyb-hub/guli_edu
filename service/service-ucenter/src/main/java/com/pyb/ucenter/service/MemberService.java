package com.pyb.ucenter.service;

import com.pyb.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-04
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);
}
