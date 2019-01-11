package com.ljn.crawler.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author李胖胖
 * @Date 2019/1/11 23:55
 * @Description:
 **/
public class NetIOUtil {
    // 前提: post请求,其他的我没试过呢   调用:   $http.post("../brand/search.do?page="+page+"&rows="+rows,xxoo);
    // 前端controller 定义变量     $scope.xxoo = {};
    //               赋值例子1:   $scope.xxoo = {"ooxx":"xxoo"}
    //                   后端接收  @RequestBody(其他形式没试过) ooxx
    //
    //               赋值例子2:   $scope.xxoo.aa = {"xxoo":"ooxx"}
    //                  后端接收 @RequestBody(其他形式没试过) aa(是个对象,必须有xxoo字段)
    //前提: post请求,其他的我没试过呢   调用:   $http.post("../brand/search.do?page="+page+"&rows="+rows,xxoo);
    //
    //          原理: 就是json传输  参考json的那天的课程  忘了是哪一天了

    static class DownloadRunnable implements Runnable {

        private List<String> imgSources;
        private String downloading;
        private File dir;

        DownloadRunnable(List<String> imgSources,File dir) {
            this.imgSources = imgSources;
            this.dir = dir;
        }

        @Override
        public void run() {
            while (true) {
                downloading = null;
                synchronized (imgSources) {
                    if (!imgSources.isEmpty()){
                        downloading = imgSources.remove(0);
                    }
                }
                if (downloading == null)
                    return;
                downloadJpg(downloading,dir);
            }
        }
    }

    public static void multiThreadDownloadJpg(List<String> imgSources, File dir) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new DownloadRunnable(imgSources,dir));
        }
    }

    //下载图片
    public static void downloadJpg(String target_url, File dir) {
        String filename = UUID.randomUUID() + ".jpg";
        File file = new File(dir, filename);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(target_url);
            URLConnection urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
