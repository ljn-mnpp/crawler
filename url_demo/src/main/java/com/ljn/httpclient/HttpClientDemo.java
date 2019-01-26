package com.ljn.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author李胖胖
 * @Date 2019/1/26 9:12
 * @Description:
 **/
public class HttpClientDemo {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(
                "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8" +
                        "&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653" +
                        "&cid3=655&page=3&s=60&click=0");
        httpGet.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/71.0.3578.98 Safari/537.36");
        CloseableHttpResponse response = client.execute(httpGet);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity,"utf-8");
        System.out.println(html);
    }
}
