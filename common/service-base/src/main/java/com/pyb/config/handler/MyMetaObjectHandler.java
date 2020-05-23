package com.pyb.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
/*自动填充时间配置类*/
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    /*在插入数据的填充内容*/
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        /*filename代表实体类属性的名称：不是数据库的字段column，所以名字是驼峰表示*/
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }


    @Override
    /*在更新数据的填充内容*/
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

}

