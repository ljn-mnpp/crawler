package com.ljn.crawler_jd.crawler;

import com.ljn.crawler_jd.dao.ItemDao;
import com.ljn.crawler_jd.entity.Item;
import com.ljn.crawler_jd.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author李胖胖
 * @Date 2019/1/26 19:39
 * @Description:
 **/
@Component  //该类在容器中是单例的   start开的是单线程的  这个例子开多线程也很容易  生成链接后用队列就行了
public class Crawler {
    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private ItemDao itemDao;

    private String baseUrl;

    private String imgDir;

    private Map<String,String> requestHeaders;

    public Crawler(){
        init();
    }
    private void init(){
        imgDir = "F:\\tencifang\\img";
        baseUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655" +
                "&s=60&click=0&page=";
        requestHeaders = new HashMap<>();
        requestHeaders.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
    }

    private Thread thread;

    class CrawlerTask implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    int page = 2 * i + 1;
                    CloseableHttpClient httpClient = httpClientUtil.getHttpClient();
                    String url = baseUrl + page;
                    HttpGet get = new HttpGet(url);
                    get.setHeaders(getHeadersFromMap(requestHeaders));
                    CloseableHttpResponse response = httpClient.execute(get);
                    if(response.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = response.getEntity();
                        String html = EntityUtils.toString(entity, "utf-8");
                        parsePage(page, html);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void parsePage(int page, String html) throws IOException {
            Document doc = Jsoup.parse(html);
            Elements items = doc.getElementsByClass("gl-item");
            System.out.println("------------------------第"+page+"页-------------------------");
            int count = 1;
            for (Element item : items) {
                System.out.println("---------------"+count+"--------------");
                String item_spu = item.attr("data-spu");
                System.out.println(item_spu);
                Elements parentDiv = item.select("div.gl-i-wrap");
                Element sku = parentDiv.select("div.p-scroll > div.ps-wrap > ul.ps-main > li.ps-item").first();
                String item_sku = sku.select("a > img").first().attr("data-sku");
                System.out.println(item_sku);
                String item_title = parentDiv.select("div.p-name > a > em").text();
                System.out.println(item_title);
                String item_price = parentDiv.select("div.p-price > strong > i").text();
                System.out.println(item_price);
                String picUrl = "https:"+parentDiv.select("div.p-img > a > img").attr("source-data-lazy-img");
                System.out.println(picUrl);
                String item_pic = downloadImg(picUrl, imgDir);
                String item_url = "https:"+parentDiv.select("div.p-img > a").attr("href");
                System.out.println(item_url);
                count++;

                Item jd_item = new Item();
                jd_item.setSpu(Long.parseLong(item_spu));
                jd_item.setSku(Long.parseLong(item_sku));
                jd_item.setTitle(item_title);
                if(StringUtils.isNoneBlank(item_price)) {
                    jd_item.setPrice(Double.parseDouble(item_price));
                }
                jd_item.setPic(item_pic);
                jd_item.setUrl(item_url);
                jd_item.setCreated(new Date());
                jd_item.setUpdated(new Date());
                itemDao.save(jd_item);

            }
        }

        private String downloadImg(String url,String dir) throws IOException {
            String filename = UUID.randomUUID() + url.substring(url.lastIndexOf("."));
            File target = new File(dir,filename);
            CloseableHttpClient httpClient = httpClientUtil.getHttpClient();
            //CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            get.setHeaders(getHeadersFromMap(requestHeaders)); //不加也行
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(target);
                entity.writeTo(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return target.getAbsolutePath();
        }

        private Header[] getHeadersFromMap(Map<String,String> headers){
            ArrayList<Header> headerList = new ArrayList<>();
            if(headers != null){
                for (Map.Entry<String,String> entry : headers.entrySet()){
                    headerList.add(new BasicHeader(entry.getKey(),entry.getValue()));
                }
            }
            return headerList.toArray(new Header[headerList.size()]);
        }
    }

    public void start(){
        thread = new Thread(new CrawlerTask());
        thread.start();
        System.out.println("开始爬取...");
    }

    public void stop(){
        thread.interrupt();
        System.out.println("停止爬取...");
    }
}
