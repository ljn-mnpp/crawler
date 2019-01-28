package com.ljn.crawlerJob.crawler;

import com.ljn.crawlerJob.entity.Job;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author李胖胖
 * @Date 2019/1/28 15:00
 * @Description:  解析    列表页  与  工作详情页
 **/
@Component
public class JobParse {

    public static String pageUrl = "https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,3.html?" +
            "lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99" +
            "&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=" +
            "&specialarea=00&from=&welfare=";

    public static String deatilsUrl = "https://jobs.51job.com/beijing-hdq/91145225.html?s=01&t=0";

    //解析薪水范围  1-1.5万/月
    public Map<String,Integer> parseSalary(String src){
        String salaryStr = src.substring(0,src.lastIndexOf("万"));
        String[] split = salaryStr.split("-");
        Map map = new HashMap();
        map.put("min",(int)(Double.parseDouble(split[0]) * 10000));
        map.put("max",(int)(Double.parseDouble(split[1]) * 10000));
        return map;
    }

    //解析发布日期  01-28发布  -->  2019-01-28
    public Date parseDate(String src) throws ParseException {
        String dateStr = src.substring(src.lastIndexOf("-")-2,src.lastIndexOf("-")+3);

        return new SimpleDateFormat("yyyy-MM-dd").parse("2019-"+dateStr);
    }

    //列表页解析
    public List<String> parseJobListHtml(Page page){
        Html html = page.getHtml();
        //css
        Selectable jobList = html.css("div.el > p.t1 > span > a");
        List<String> jobUrl = jobList.css("a", "href").all();
        return jobUrl;
    }

    //工作详情页解析
    String center = "/html/body/div[3]/div[2]";
    String tHeader = center+"/div[2]";
    String tCompany_main = center+"/div[3]";
    public Job parseJobHtml(Page page){
        Html html = page.getHtml();
        String url = page.getRequest().getUrl();

        Job job = new Job();
        //url
        job.setUrl(url);

        //公司名称
        String companyName =
                html.xpath(tHeader + "/div[1]/div[1]/p[@class=cname]/a/text()").get();
        job.setCompanyName(companyName);

        //工作详情   这个还是用jsoup 原生API比较好用 解析结果很好
        //Selectable jobInfo = html.xpath(tCompany_main + "/div[1]/div//text()");
        Document doc = html.getDocument();
        Elements bmsg_job_msg_inbox = doc.getElementsByClass("bmsg job_msg inbox");
        job.setJobInfo(bmsg_job_msg_inbox.text());

        //工作地址
        Selectable addr = html.xpath(tCompany_main + "/div[2]/div/p//text()");
        job.setCompanyAddr(addr.get());
        job.setJobAddr(addr.get());

        //公司信息
        Selectable copInfo = html.xpath(tCompany_main + "/div[3]/div/text()");
        job.setCompanyInfo(copInfo.get());

        //工作名称
        Selectable jobName = html.xpath(tHeader + "/div[1]/div[1]/h1/text()");
        page.putField("jobName",jobName.all());
        job.setJobName(jobName.get());

        //发布时间
        Selectable time = html.xpath(tHeader + "/div[1]/div[1]/p[@class=msg]/text()");
        try {
            job.setTime(parseDate(time.get()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //薪资范围
        Selectable salary = html.xpath(tHeader + "/div[1]/div[1]/strong/text()");
        Map<String, Integer> map = parseSalary(salary.get());
        job.setSalaryMin(map.get("min"));
        job.setSalaryMax(map.get("max"));

        return job;
    }
    
}
