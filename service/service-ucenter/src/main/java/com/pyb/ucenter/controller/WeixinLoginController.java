package com.pyb.ucenter.controller;


import com.google.gson.Gson;
import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.ucenter.entity.Member;
import com.pyb.ucenter.service.MemberService;
import com.pyb.ucenter.utils.HttpClientUtils;
import com.pyb.ucenter.utils.WxConstants;
import com.pyb.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Objects;

/*微信登录*/
@CrossOrigin
@Controller/*不能用restcontroller*/
@RequestMapping("/api/ucenter/wx")
public class WeixinLoginController {


    @Autowired
    private MemberService memberService;

    /*1.生成微信登录的二维码*/
    @GetMapping("/login")
    public String getWxCode() {
        //固定地址，后面拼接参数
        //String url = "https://open.weixin.qq.com/" +
        //"connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位；固定写法
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = WxConstants.WX_OPEN_REDIRECT_URL;

        try {

            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                WxConstants.WX_OPEN_APP_ID,
                redirectUrl,
                "pyb"
        );

        //扫码成功后重定向到第二步的地址
        return "redirect:" + url;
    }


    /*2.获取扫描人信息、添加信息；扫码后跳转的地址*/
    @GetMapping("/callback")
    public String callBack(String code, String state) {
        //1.第一步成功后返回code值，临时票据，类似于验证码

        try {
            //2.拿着code请求，访问微信固定的地址，得到两个值 access_token 和openId
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    WxConstants.WX_OPEN_APP_ID,
                    WxConstants.WX_OPEN_APP_SECRET,
                    code
            );

            //请求这个拼接好的地址，得到返回两个值 access_token 和 openId
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo 字符串取出来两个值 accessToken 和 openId
            //把accessTokenInfo 字符串转换成map集合，根据map里面可以 获取对应的值

            //使用json转换工具
            Gson gson = new Gson();
            HashMap<String, String> accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);

            String accessToken = accessTokenMap.get("access_token");

            String openid = accessTokenMap.get("openid");


            //把扫描人信息添加到数据库里面
            //判断数据库表里面是否存在相同微信信息，根据openid判断，有的话不用访问微信提供的网址来获取用户信息
            Member member = memberService.getMemberByOpenid(openid);
            if (member == null){
                //3.拿着得到access_token和openid ,再去请求微信提供的固定地址，获取到扫描人信息
                // 访问微信的资源服务器，获取到用户信息

                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openid
                );

                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //获取返回的userInfo信息
                HashMap<String, String> userInfoMap = gson.fromJson(userInfo, HashMap.class);

                //昵称
                String nickname = userInfoMap.get("nickname");
                //头像
                String headimgurl = userInfoMap.get("headimgurl");

                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            //使用jwt根据member对象生成token字符串，不能直接用cookie放对象传递，cookie传递不能跨域
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            //最后: 返回前端首页面的地址，通过路径传递token字符串，这里跳转的地址发布后要修改
            return "redirect:http://localhost:3000?token=" + token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"登录失败");
        }

    }

}
