package com.pyb.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
/*取的配置文件中的值，在这里集中获取，方便修改*/
public class VodConstants implements InitializingBean {

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    /*上面取完value之后执行方法，给静态变量赋值*/
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
