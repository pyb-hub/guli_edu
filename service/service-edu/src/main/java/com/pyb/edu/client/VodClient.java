package com.pyb.edu.client;

import com.pyb.edu.client.impl.VodClientImpl;
import com.pyb.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*要调用的客户端,fallback:服务熔断之后调用的方法*/
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    /*调用的方法:这里的PathVariable一定要加注解，否则报错*/
    @DeleteMapping("/edu/vod/removeVideo/{sourceId}")
    Result removeVideo(@PathVariable("sourceId") String sourceId);

}
