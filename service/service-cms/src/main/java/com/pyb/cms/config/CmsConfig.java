package com.pyb.cms.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration/*作为配置类*/
@MapperScan("com.pyb.cms.mapper")/*dao层的扫描包，或者在dao里面类上加入mapper注解*/
public class CmsConfig {

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件，还要在实体类加一个@version注解就行，修改的时候，需要先把整体对象查询出来，作为修改的参数，
     * 不能直接new对象，作为修改的参数；
     */
    /*@Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
*/

}
