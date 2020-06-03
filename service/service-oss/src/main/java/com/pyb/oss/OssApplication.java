package com.pyb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)/*不扫描配置文件的数据库配置，防止报错-找不到数据库访问路径*/
@ComponentScan(basePackages = {"com.pyb"})/*该项目启动也能扫描到公共配置类里面的配置*/
@EnableDiscoveryClient/*向nacos注册服务*/
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
