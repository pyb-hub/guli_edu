package com.pyb.canal;

import com.pyb.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;


@SpringBootApplication
@CrossOrigin
public class CanalApplication implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;


    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }

    /*监听远程数据库和同步数据到本地*/
    @Override
    public void run(String... args) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }
}
