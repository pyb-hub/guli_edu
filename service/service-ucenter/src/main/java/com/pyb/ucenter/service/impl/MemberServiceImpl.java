package com.pyb.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.ucenter.entity.Member;
import com.pyb.ucenter.entity.vo.RegisterVo;
import com.pyb.ucenter.mapper.MemberMapper;
import com.pyb.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyb.utils.JwtUtils;
import com.pyb.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    /*用手机号登录*/
    public String login(Member member) {
        String mobile = member.getMobile();
        String passwordInput = member.getPassword();
        /*输入的密码加密传入数据库中*/
        String password = MD5Utils.transferInputToDb(passwordInput);

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(20001,"账号或者密码为空");
        }

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        wrapper.eq("password",password);
        Member one = this.getOne(wrapper);
        if (one == null){
            throw new MyException(20001,"账号或者密码为错");
        }
        Boolean isDisabled = one.getIsDisabled();
        if (isDisabled){
            throw new MyException(20001,"账号被禁用");
        }

        /*登录成功,根据id和nickname生成的返回token*/
        String token = JwtUtils.getJwtToken(one.getId(), one.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        /*获取注册的数据*/
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(password)){
            throw new MyException(20001,"注册失败");
        }

        /*判断redis中的验证码是否正确*/
        String codeRedis = redisTemplate.opsForValue().get(mobile + "MsgCode");
        if (!code.equals(codeRedis)) {
            throw new MyException(20001,"验证码错误");
        }
        /*判断手机号是否重复*/
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        int count = this.count(wrapper);
        if(count > 0) {
            throw new MyException(20001,"手机已经被注册");
        }

        /*判断昵称是否重复*/
        QueryWrapper<Member> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("nickname",nickname);
        int count2 = this.count(wrapper2);
        if(count2 > 0) {
            throw new MyException(20001,"昵称被注册");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        /*密码二次加密*/
        member.setPassword(MD5Utils.transferInputToDb(password));
        /*设置默认头像，后续可更改*/
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        this.save(member);
    }

    @Override
    public Member getMemberByOpenid(String openid) {
        /*判断是否微信扫码登录了*/
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return this.getOne(wrapper);
    }

    @Override
    public Integer getOneDayRegisterNum(String day) {
        return baseMapper.getOneDayRegisterNum(day);
    }
}
