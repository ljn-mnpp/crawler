package com.ljn;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Author李胖胖
 * @Date 2019/1/27 8:49
 * @Description:
 **/
public class MyProcessor implements PageProcessor{
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
//        Document doc = html.getDocument();

//        String title = page.getHtml().css("title").get();
//        page.putField("title",title);

//        page.getResultItems().put("title",title);

        Selectable title = page.getHtml().$("meta");
        page.getResultItems().put("meta",title.get());  //只返回第一个结果
        page.putField("all",title.all());

        Selectable links = html.css("div.header_con").links();
        List<String> list = links.all();
        page.putField("links",list);
    }

    private Site site  = Site.me();

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MyProcessor()).addUrl("http://www.itcast.cn").start();
    }
}
