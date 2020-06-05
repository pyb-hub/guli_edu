package com.pyb.msm.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pyb.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean sendMsg(String phone,String code) {

        if (StringUtils.isEmpty(phone)){
            return false;
        }

        /*传入阿里云的id和秘钥*/
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G3DVT8JtiSM37kUZi5y", "nMqWUXQGmp0ht8zLZbNi573zmCpPIs");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数：不修改
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");


        //设置发送相关的参数
        //手机号
        request.putQueryParameter("PhoneNumbers",phone);
        //申请阿里云短信服务的签名名称
        request.putQueryParameter("SignName","我的咕咕电商网站");
        //申请阿里云短信服务的模板code
        request.putQueryParameter("TemplateCode","SMS_192380708");

        //验证码数据，转换json类型key-value数据传递，所以需要map类型才能转化为key-value
        HashMap<String,String> map = new HashMap<>();
        map.put("code",code);
        String jsonCode = JSON.toJSONString(map);
        request.putQueryParameter("TemplateParam",jsonCode);

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
