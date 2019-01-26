package com.ljn.crawler_jd.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author李胖胖
 * @Date 2019/1/26 19:37
 * @Description:
 **/
@Component
public class HttpClientUtil {

    @Autowired
    private PoolingHttpClientConnectionManager pm;

    public CloseableHttpClient getHttpClient(){
        return HttpClients.custom().setConnectionManager(pm).build();
    }
}
