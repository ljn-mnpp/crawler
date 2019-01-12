package com.ljn.crawler.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author李胖胖
 * @Date 2019/1/11 21:05
 * @Description: 爬取目标资源工具类
 **/
public class SimpleURLCrawlerUtil {

    //测试链接
    public static void main(String[] args) throws IOException {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Accept-Charset","UTF-8");
        headers.put("Accept-Language","zh-CN,zh");
        headers.put("Content-type","UTF-8");
        //testPattern();
    }

    // 这个是测试模式匹配用的             链接地址               目标模式             请求参数                 是否打印页面源码
    public static void testPattern(String target_url, String target_pattern, Map<String,String> requestHeaders,boolean showPageCode) throws Exception {
        URL url = new URL(target_url);
        URLConnection urlConnection = url.openConnection();
        //设置请求头
        if(requestHeaders != null){
            for (Map.Entry<String,String> header : requestHeaders.entrySet()){
                urlConnection.addRequestProperty(header.getKey(),header.getValue());
            }
        }

        //获取流
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String s = null;

        Pattern p = Pattern.compile(target_pattern);
        Matcher m = p.matcher("");

        //页面源码
        List<String> urlText = new LinkedList<>();
        //匹配结果
        List<String> results = new ArrayList<>();
        while ((s =bf.readLine())!=null){
            urlText.add(s);
            m.reset(s);
            while (m.find()){
                String group = m.group();
                results.add(group);
            }
        }
        bf.close();
        if(showPageCode) {
            System.out.println("=====================页面源码=================");
            CollectionUtil.sout(urlText);
        }
        System.out.println("=====================匹配结果=================");
        CollectionUtil.sout(results);
    }

    //匹配目标链接, 参数: 种子链接  目标模式
    public static LinkedList<String> getTargetURLS(String urlStr, String target_pattern){
        LinkedList<String> results = new LinkedList<>();
        try {
            //链接目标url,获取流
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bfr = null;

            try {
                bfr = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

                //设置匹配模式
                Pattern p_target = Pattern.compile(target_pattern);
                Matcher m_target = p_target.matcher("");

                String s = null;
                while ((s = bfr.readLine())!=null){
                    m_target.reset(s);
                    while (m_target.find()){
                        String group = m_target.group();
                        results.add(group);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bfr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }


}
