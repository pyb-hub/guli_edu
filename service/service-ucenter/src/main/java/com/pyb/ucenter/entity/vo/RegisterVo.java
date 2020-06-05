package com.pyb.ucenter.entity.vo;

import com.pyb.ucenter.entity.Member;
import lombok.Data;

@Data
public class RegisterVo extends Member {

    /*手机验证码*/
    private String code;

}
