package com.ljn.crawlerJob.crawler;

import com.ljn.crawlerJob.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @Author李胖胖
 * @Date 2019/1/28 15:12
 * @Description:   页面解析核心业务逻辑
 **/
@Component
public class PageProcess51Job implements PageProcessor {

    @Autowired
    private JobParse jobParse;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        //判定是列表页还是详情页
        String url = page.getRequest().getUrl();
        boolean isListPage = url.startsWith("https://search.51job.com/list");

        if(isListPage){
            //解析列表页  添加详情页url
            List<String> urlList = jobParse.parseJobListHtml(page);
            page.addTargetRequests(urlList);
        }else {
            //解析详情页
            page.putField("分割符","--------------------解析结果---------------------");
            Job job = jobParse.parseJobHtml(page);
            page.putField("job",job);
            page.putField("分割符","--------------------解析结果---------------------");
        }
    }

    @Override
    public Site getSite() {
        return new Site();
    }

}
