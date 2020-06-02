package com.pyb.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pyb"})/*该项目启动也能扫描到公共配置类里面的配置*/
public class EduApplication {

    public static void main(String[] args) {

        SpringApplication.run(EduApplication.class,args);
    }
}
