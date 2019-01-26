package com.ljn.crawler_jd.controller;

import com.ljn.crawler_jd.crawler.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author李胖胖
 * @Date 2019/1/26 20:46
 * @Description:
 **/
@Controller
@RequestMapping("/crawler")
public class CrawlerController {
    @Autowired
    private Crawler crawler;

    @RequestMapping("/start")
    @ResponseBody
    public String start(){
        crawler.start();
        return "开始爬取";
    }

    @RequestMapping("/stop")
    @ResponseBody
    public String stop(){
        crawler.stop();
        return "爬取结束";
    }
}
