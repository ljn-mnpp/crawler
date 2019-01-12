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

    //  下载任务
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
            TimeUtils t = new TimeUtils();
            t.start(Thread.currentThread() + "爬取链接...");
            while (true) {
                downloading = null;
                synchronized (imgSources) {
                    if (!imgSources.isEmpty()){
                        downloading = imgSources.remove(0);
                    }
                }
                if (downloading == null) {
                    t.end();
                    return;
                }
                downloadJpg(downloading,dir);
            }
        }
    }

    //多线程下载
    public static void multiThreadDownloadJpg(List<String> imgSources, File dir,Integer threadpoolSize) {
        System.out.println("==============================================================");
        System.out.println("开始爬取...最大线程数:"+threadpoolSize);
        System.out.println("目标资源数量: "+imgSources.size());
        System.out.println("警告:线程池未设置自动结束参数...如果所有线程均已爬取结束,停止程序即可.");
        System.out.println("===============================================================");
        ExecutorService executorService = Executors.newFixedThreadPool(threadpoolSize);
        for (int i = 0; i < threadpoolSize; i++) {
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
