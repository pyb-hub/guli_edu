package com.pyb.acl.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IndexService {

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);

}
