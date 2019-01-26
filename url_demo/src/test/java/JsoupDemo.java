import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author李胖胖
 * @Date 2019/1/26 17:56
 * @Description:
 **/
public class JsoupDemo {

    @Test
    public void t1() throws IOException {
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn"),1000);
        Element title = doc.getElementsByTag("title").first();
        System.out.println(title.text());
    }

    @Test
    public void t2() throws IOException {
        File file = new File("file/test.html");
        Document document = Jsoup.parse(file, "utf-8");
//        Element title = document.getElementsByTag("title").first();
//        System.out.println(title.text());
//
//        Element city_bj = document.getElementById("city_bj");
//        System.out.println(city_bj.text());
//
//        Elements byClass = document.getElementsByClass("class_a class_b");
//        for (Element aClass : byClass) {
//            System.out.println(aClass.text());
//        }

        //不好使
/*        Elements target = document.getElementsByAttribute("href");
        for (Element element : target) {
            System.out.println(target);
        }*/

        Elements elementsByAttributeValue = document.getElementsByAttributeValue("class", "s_name");
        for (Element element : elementsByAttributeValue) {
            System.out.println(element);
            System.out.println(element.id());
            System.out.println(element.text());
            System.out.println(element.attributes());
            System.out.println(element.attr("class"));
            System.out.println(element.classNames());
        }
    }

    @Test  //选择器  京东
    public void t3() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655" +
                "&page=3&s=60&click=0");
        get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "utf-8");
        Document doc = Jsoup.parse(html);

        Elements itemList = doc.getElementsByClass("gl-item");
        System.out.println(itemList.size());
        Element first = itemList.first();

        String sku = first.attr("data-sku");
        System.out.println("sku: "+sku);
        String spu = first.attr("data-spu");
        System.out.println("spu: "+spu);
        Elements select = first.select("div.gl-i-wrap > div.p-name > a > em");
        String title = select.text();
        System.out.println(title);
        String price = first.select("div.gl-i-wrap > div.p-price > strong > i").text();
        System.out.println(price);
        String imgUrl = "https"+first.select("div.gl-i-wrap > div.p-img > a > img")
                .attr("source-data-lazy-img");
        System.out.println(imgUrl);

    }


}
