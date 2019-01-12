package com.ljn.crawler.demo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDemo {
    public static void main(String[] args) throws IOException {
        String urlStr = "http://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=%E7%BE%8E%E5%A5%B3";
        String urlStr_1 = "http://www.win4000.com/meitu.html";
        URL url = new URL(urlStr_1);
        URLConnection urlConnection = url.openConnection();
        System.out.println("ContentType: "+urlConnection.getContentType());
        System.out.println("ContentEncoding: "+urlConnection.getContentEncoding());
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String s = null;
        BufferedWriter bfw = new BufferedWriter(new FileWriter("D:\\project\\crawler\\url_demo\\file\\"+"meitu"));

        Pattern p = Pattern.compile("http:.*.jpg");
        Matcher m = p.matcher("");
        List<String> jpg_list =new LinkedList<>();
        while ((s =bf.readLine())!=null){
            //System.out.println(s);
            bfw.write(s);
            bfw.newLine();
            m.reset(s);
            while (m.find()){
                String group = m.group();
                System.out.println(group);
                jpg_list.add(group);
            }
        }
        bfw.close();
        bf.close();
    }


}
