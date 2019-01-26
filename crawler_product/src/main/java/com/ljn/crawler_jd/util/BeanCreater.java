package com.ljn.crawler_jd.util;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author李胖胖
 * @Date 2019/1/26 19:36
 * @Description:
 **/
@Configuration
public class BeanCreater {

    @Bean
    public PoolingHttpClientConnectionManager createHttpClientConnectionManager(){
        return new PoolingHttpClientConnectionManager();
    }
}
