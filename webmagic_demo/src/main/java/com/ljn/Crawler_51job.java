package com.ljn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
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
 * @Date 2019/1/28 13:09
 * @Description:
 **/
public class Crawler_51job {

    private static String pageUrl = "https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,3.html?" +
            "lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99" +
            "&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=" +
            "&specialarea=00&from=&welfare=";

    private static String deatilsUrl = "https://jobs.51job.com/beijing-hdq/91145225.html?s=01&t=0";

    static class PageProcess51Job implements PageProcessor{

        //列表页解析
        private void parseJobListHtml(Page page){
            Html html = page.getHtml();
            //css
            Selectable jobList = html.css("div.el > p.t1 > span > a");
            List<String> jobUrl = jobList.css("a", "href").all();
            List<String> jobName = jobList.css("a", "text").all();


            //xpath  方法用错了 好JB尴尬
//            Selectable jobList = html
//                    .$("/html/body/div[@class=dw_wp]/div[@class=dw_table]/div[@class=el]" +
//                            "/p[@class=t1]/span/a");
//            List<String> jobUrl = jobList.$("attribute::href").all();
//            List<String> jobName = jobList.$("child::text()").all();

            page.putField("jobUrl",jobUrl);
            page.putField("jobName",jobName);


        }

        @Override
        public void process(Page page) {
            Html html = page.getHtml();

            //判定是列表还是详情页
            String url = page.getRequest().getUrl();
            boolean isListPage = url.startsWith("https://search.51job.com/list");
            if(isListPage){
                parseJobListHtml(page);
            }else {
//                Selectable jobDiv = html
//                        .css("body > div.tCompanyPage > div.tCompany_center");
//                Selectable main = jobDiv.css("div.tCompany_main");

                String center = "/html/body/div[3]/div[2]";
                String tHeader = center+"/div[2]";
                String tCompany_main = center+"/div[3]";

                page.putField("分割符","------------------------------------------");

                //url
                page.putField("url",url);

                //公司名称
                String companyName =
                        html.xpath(tHeader + "/div[1]/div[1]/p[@class=cname]/a/text()").get();
                page.putField("companyName",companyName);

                //工作详情
                //Selectable jobInfo = html.xpath(tCompany_main + "/div[1]/div/text(*)");
                Document doc = html.getDocument();
                Elements bmsg_job_msg_inbox = doc.getElementsByClass("bmsg job_msg inbox");
                String text = bmsg_job_msg_inbox.text();
                page.putField("jobInfo",text);

                //工作地址
                Selectable addr = html.xpath(tCompany_main + "/div[2]/div/p//text()");
                page.putField("addr",addr.all());

                //公司信息
                Selectable copInfo = html.xpath(tCompany_main + "/div[3]/div/text()");
                page.putField("copInfo",copInfo.all());

                //工作名称
                Selectable jobName = html.xpath(tHeader + "/div[1]/div[1]/h1/text()");
                page.putField("jobName",jobName.all());

                //发布时间
                Selectable time = html.xpath(tHeader + "/div[1]/div[1]/p[@class=msg]/text()");
                try {
                    page.putField("time",parseDate(time.get()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //薪资范围
                Selectable salary = html.xpath(tHeader + "/div[1]/div[1]/strong/text()");
                page.putField("salary",parseSalary(salary.get()));
            }
        }

        @Override
        public Site getSite() {
            return new Site();
        }

        //解析薪水范围  1-1.5万/月
        private Map<String,Integer> parseSalary(String src){
            String salaryStr = src.substring(0,src.lastIndexOf("万"));
            String[] split = salaryStr.split("-");
            Map map = new HashMap();
            map.put("min",(int)Double.parseDouble(split[0]) * 10000);
            map.put("max",(int)Double.parseDouble(split[1]) * 10000);
            return map;
        }

        //解析发布日期  01-28发布  -->  2019-01-28
        private Date parseDate(String src) throws ParseException {
            String dateStr = src.substring(src.lastIndexOf("-")-2,src.lastIndexOf("-")+3);

            return new SimpleDateFormat("yyyy-MM-dd").parse("2019-"+dateStr);
        }
    }

    public static void main(String[] args) {
        Spider.create(new PageProcess51Job())
                .addUrl(deatilsUrl)
                .run();
    }
}
