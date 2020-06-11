package com.pyb.sta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pyb"})/*该项目启动也能扫描到公共配置类里面的配置*/
@EnableDiscoveryClient/*注入服务到注册中心：允许被发现*/
@EnableFeignClients
@EnableScheduling  /*开启定时器注解*/
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
