package com.ljn.crawlerJob.controller;

import com.ljn.crawlerJob.crawler.Crawler_51job;
import com.ljn.crawlerJob.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author李胖胖
 * @Date 2019/1/28 14:17
 * @Description:
 **/
@RestController
public class JobController {

    @Autowired
    private Crawler_51job crawler_51job;

    @Autowired
    private JobService jobService;

    @RequestMapping("/start")
    public String start(Integer pageNum){
        if(pageNum != null){
            crawler_51job.start(pageNum);
        }else {
            crawler_51job.start(1);
        }
        return "开始爬取...";
    }

    @RequestMapping("/stop")
    public String stop(){
        crawler_51job.stop();
        return "已停止...";
    }

    @RequestMapping("/fa")
    public List findAll(){
        return jobService.findAll();
    }

    @RequestMapping("/del")
    public String del(){
        jobService.delAll();
        return "已清除";
    }
}
