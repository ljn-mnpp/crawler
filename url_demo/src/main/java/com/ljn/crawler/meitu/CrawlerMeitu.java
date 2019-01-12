package com.ljn.crawler.meitu;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    自动下载 http://www.win4000.com/meitu.html 站点下美女图片

    目标图片资源模式:"http://pic1.win4000.com//pic/3/5d/c1b01f3b28.jpg"   注意pic后边的位数 区分小图片资源

    深度 2

    算法:
        建立链接获取流,缓冲页面源码
        匹配网页所有超链接和图片链接
            匹配超链接<href>,记录该链接,递归爬取该链接
            匹配 http://pic1.win4000.com//pic/(.*{15,20}) 保存到list

        list 写入文档
    先保存图片资源,别下载...
 */

/**
 * @Author 李佳楠
 * @Description:  这个类是随便打的,刚开始学  很乱,别看了...
 * @Date  2019/1/12
 **/
public class CrawlerMeitu {

    //起始链接
    private static String target_url = "http://www.win4000.com/meitu.html";
    //深度
    private static int default_deep = 2;
    //目标模式
    private static String pattern = "http://pic1.win4000.com//pic/(.{15,20})jpg";
    //结果链接缓存
    private static LinkedList<String> results = new LinkedList<>();
    //结果链接存储文件
    private static String results_path = "D:\\project\\crawler\\url_demo\\file\\results";
    //结果图片下载路径
    private static String path = "e:/baby/";

    public static void main(String[] args) throws IOException {
        start(results_path);
        loadImgfromfile(results_path,path);
    }

    //多线程下载图片
    private static void loadImgfromfile(String src_path,String dir_path) throws IOException {
        LinkedList<String> urls = new LinkedList<>();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(src_path)));
        String s = null;
        while ((s = bfr.readLine())!=null){
            urls.add(s);
        }
        bfr.close();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true) {
                    String url = null;
                    synchronized (urls) {
                        if (!urls.isEmpty()) {
                            url = urls.removeFirst();
                        }
                    }
                    if (url == null)
                        return;
                    downloadJpg(url, path);
                }
            }).start();
        }
    }

    //start
    private static void start(String result_path) throws IOException {
        recursion(default_deep,target_url,pattern,results);
        System.out.println(results.size());
        for (String result : results) {
            System.out.println(result);
        }

        saveResults(result_path,results);
    }

    //测试
    private static void patternTest() {
        String s = "http://pic1.win4000.com//pic/3/5d/c1b01f3b28.jpg";
        String s1 = "http://pic1.win4000.com/pic/d/db/79edbbe99e_250_350.jpg";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s1);
        while (m.find()){
            System.out.println(m.group());
        }
    }

    //核心递归爬取方法 参数: 深度  种子链接  目标链接模式  目标链接结果集
    private static void recursion(int deep,String urlStr, String target_pattern, List<String> targets){
        System.out.println("爬取深度: "+(default_deep - deep +1));
        if(--deep < 0)
            return;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String s = null;

            Pattern p_href = Pattern.compile("http.*\\.html");
            Matcher m_href = p_href.matcher("");
            Pattern p_target = Pattern.compile(target_pattern);
            Matcher m_target = p_target.matcher("");

            while ((s = bfr.readLine())!=null){
                m_href.reset(s);
                while (m_href.find()){
                    String group = m_href.group();
                    recursion(deep,group,target_pattern,targets);
                }

                m_target.reset(s);
                while (m_target.find()){
                    String group = m_target.group();
                    targets.add(group);
                }
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //保存结果
    private static void saveResults(String path,List<String> list) throws IOException {
        FileWriter fw = new FileWriter(path);
        BufferedWriter bfw = new BufferedWriter(fw);
        for (String o : list) {
            bfw.write(o);
            bfw.newLine();
        }
        bfw.close();
    }

    //下载图片
    private static void downloadJpg(String target_url,String dir_path){
        UUID uuid = UUID.randomUUID();
        String path = dir_path + uuid + ".jpg";
        BufferedInputStream bis = null;
        BufferedOutputStream  bos = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(target_url);
            URLConnection urlConnection = url.openConnection();

            //System.out.println(urlConnection.getContentType()); //image/jpeg
            //System.out.println(urlConnection.getContentEncoding()); //null
            //System.out.println(urlConnection.getContent()); //sun.awt.image.URLImageSource

            inputStream = urlConnection.getInputStream();
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes))!= -1){
                bos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
