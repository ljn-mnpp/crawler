import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author李胖胖
 * @Date 2019/1/27 8:47
 * @Description:
 **/
public class T1 {


    private String pageUrl = "https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,3.html?" +
            "lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99" +
            "&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=" +
            "&specialarea=00&from=&welfare=";

    private String deatilsUrl = "https://jobs.51job.com/beijing-hdq/91145225.html?s=01&t=0";

    static class JobProcessor implements PageProcessor{

        @Override
        public void process(Page page) {
            Html html = page.getHtml();
            //System.out.println(html.get());

            //判定是列表还是详情页
            String url = page.getRequest().getUrl();
            boolean isListPage = url.startsWith("https://search.51job.com/list");
            if(isListPage){
                parseJobListHtml(html,page);
            }else {
//                Selectable jobDiv = html
//                        .css("body > div.tCompanyPage > div.tCompany_center");
//                Selectable main = jobDiv.css("div.tCompany_main");

                String tCompany_main = "/html/body/div[3]/div[2]/div[3]";
                Selectable jobInfo = html.xpath(tCompany_main + "/div[1]/div/p//span/text()");
                page.putField("jobInfo",jobInfo.all());

            }

        }

        //列表页解析
        private void parseJobListHtml(Html html,Page page){

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
        public Site getSite() {
            return new Site();
        }
    }

    @Test
    public void regexT(){
        Pattern pageP = Pattern.compile("https://search\\.51.job\\.com/list/.*\\.html");
        Matcher m = pageP.matcher(pageUrl);
    }

    @Test
    public void test(){
        Request request = new Request();
        request.setUrl(pageUrl);
        request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

        Spider.create(new JobProcessor())
                .addUrl(deatilsUrl)
                //.addRequest(request)
                .run();

    /*    try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
