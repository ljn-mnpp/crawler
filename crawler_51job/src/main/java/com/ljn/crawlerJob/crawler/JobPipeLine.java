package com.ljn.crawlerJob.crawler;

import com.ljn.crawlerJob.entity.Job;
import com.ljn.crawlerJob.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @Author李胖胖
 * @Date 2019/1/28 15:54
 * @Description:  持久化结果至数据库
 **/
@Component
public class JobPipeLine implements Pipeline{

    @Autowired
    private JobService jobService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Object job = resultItems.get("job");
        if(job != null){
            jobService.save((Job)job);
        }
    }
}
