package com.ljn.crawlerJob.crawler;

import com.ljn.crawlerJob.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author李胖胖
 * @Date 2019/1/28 13:09
 * @Description:   起始链接地址生成  pageprocessor jobPipeLine Spider对象的创建 控制
 **/

@Component
public class Crawler_51job {

    private Spider spider;

    @Autowired
    private PageProcess51Job pageProcess51Job;

    @Autowired
    private JobPipeLine jobPipeLine;

    //搜索关键字 java  地域 北京
    private String listPageUrlPre = "https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,";
    private String listPageUrlPost = ".html?" +
            "lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99" +
            "&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=" +
            "&specialarea=00&from=&welfare=";

    private String[] createUrl(int pages){
        String[] url = new String[pages];
        for (int i = 0; i < pages; i++) {
            url[i] = listPageUrlPre + (i+1) + listPageUrlPost;
        }
        return url;
    }

    public void start(int pages){
        String[] url = createUrl(pages);
        this.spider = Spider.create(pageProcess51Job)
                .addPipeline(jobPipeLine)
                .setScheduler(new QueueScheduler().setDuplicateRemover(
                        new BloomFilterDuplicateRemover(10000000)
                ))
                .thread(5)
                .addUrl(url);
        spider.start();
    }

    public void stop(){
        spider.stop();
    }

}
