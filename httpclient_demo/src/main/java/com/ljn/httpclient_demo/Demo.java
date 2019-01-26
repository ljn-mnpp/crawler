package com.ljn.httpclient_demo;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.HttpClientUtil;
import utils.HttpsUtils;
import utils.SSLClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    //private static String url = "https://www.baidu.com";
    private static String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&suggest=2.def.0.V13&wq=shouji&pvid=4c3eb8a3ed784aec8487862c72d9c80d";
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new SSLClient();
        HttpUriRequest request = new HttpGet(url);
        //request.addHeader("Connection","keep-alive");
        //request.addHeader("Cache-Control","max-age=0");
        request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        //request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        //request.addHeader("Accept-Encoding","gzip, deflate, br");
        //request.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        HttpEntity entity = response.getEntity();
        System.out.println(entity.getContentLength());
        System.out.println(entity.getContentType());

        String s = EntityUtils.toString(entity,"utf-8");
        System.out.println(s);


      /*  Header contentEncoding = entity.getContentEncoding();
        if(contentEncoding != null) {
            HeaderElement[] elements = contentEncoding.getElements();
            for (HeaderElement element : elements) {
                System.out.println(element.getName() + element.getValue());
            }
        }

        InputStream content = entity.getContent();
        sout(content);*/
    }

    private static void sout(InputStream inputStream) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String s = null;
        while ((s = bfr.readLine())!= null){
            System.out.println(s);
        }
        bfr.close();

    }

    private static void http_t() throws Exception {
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建 服务器主机  请求
        HttpHost host = new HttpHost("www.baidu.com",8080);
        HttpUriRequest request = new HttpGet("/");

        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(host, request);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        Header[] headers = httpResponse.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName()+":"+header.getValue());
        }

        HttpEntity entity = httpResponse.getEntity();
        InputStream content = entity.getContent();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(content));
        String s = null;
        while ((s = bfr.readLine()) != null){
            System.out.println(s);
        }
        bfr.close();
    }


    private static void https_t() throws Exception {
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建  请求
        HttpUriRequest request = new HttpGet("https://www.baidu.com");

        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(request);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        Header[] headers = httpResponse.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName()+":"+header.getValue());
        }

        HttpEntity entity = httpResponse.getEntity();
        InputStream content = entity.getContent();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(content));
        String s = null;
        while ((s = bfr.readLine()) != null){
            System.out.println(s);
        }
        bfr.close();
    }
}
