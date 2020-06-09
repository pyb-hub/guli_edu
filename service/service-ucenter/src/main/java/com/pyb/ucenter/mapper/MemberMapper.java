package com.pyb.ucenter.mapper;

import com.pyb.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-04
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer getOneDayRegisterNum(@Param("day") String day);
}
