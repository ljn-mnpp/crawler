import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author李胖胖
 * @Date 2019/1/26 9:18
 * @Description:
 **/
public class ClientDemo {

    @Test
    public void testPost() throws IOException {
        HttpPost post = new HttpPost("http://www.itcast.cn");

        //请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000) //建立链接
                .setConnectionRequestTimeout(500)  //获取链接?
                .setSocketTimeout(10 * 1000)       //传输数据
                .build();

        //请求体
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keys","java"));
        UrlEncodedFormEntity form = new UrlEncodedFormEntity(params,"utf-8");
        post.setEntity(form);
        post.setConfig(requestConfig);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse  response = client.execute(post);

        if(response.getStatusLine().getStatusCode() == 200)
            System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));

        response.close();
        client.close();

    }

    @Test
    public void pool() throws IOException {
        PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager();
        pm.setMaxTotal(200);
        //每个主机最大并发数
        pm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pm).build();

        CloseableHttpResponse response = httpClient.execute(new HttpGet("https://www.baidu.com"));
        System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
    }

    @Test
    public void img() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://img10.360buyimg.com/n7/jfs/t21352/96/1157331654/101234/884d217c/5b20f8e6Nced8b612.jpg");
        CloseableHttpResponse r = httpClient.execute(get);
        int statusCode = r.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = r.getEntity();
        entity.writeTo(new FileOutputStream("F:\\tencifang\\img\\t.jpg"));
        r.close();
        httpClient.close();
    }

    @Test
    public void xiami() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://www.baidu.com");
        get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        get.addHeader("Accept","text/html");
        CloseableHttpResponse r = httpClient.execute(get);
        int statusCode = r.getStatusLine().getStatusCode();
        System.out.println(EntityUtils.toString(r.getEntity(),"utf-8"));
    }

}
//GET https://www.xiami.com/ HTTP/1.1
//Host: www.xiami.com
//Connection: keep-alive
//Cache-Control: max-age=0
//Upgrade-Insecure-Requests: 1
//User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//Accept-Encoding: gzip, deflate, br
//Accept-Language: zh-CN,zh;q=0.9
//Cookie: xmgid=0a3d1a96-bd75-4c7b-84c9-3aefd7d60f84; xm_sg_tk=59bdc8f4ebc546c9675a396a9c979f06_1548514346466; xm_sg_tk.sig=ALer_mv5n8VkE8mrCg9_LU98-P3B30vfrAgjERAGG04; _uab_collina=154851434672310593574386; cna=9iN7FGQhe2QCAdzDQlQ32MSi; gid=154851443533322; PHPSESSID=7a73e72f8cfd403506ad064183a937e9; _xiamitoken=d6a0250cc96f3b25b3a7fb2a7ec3c020; _unsign_token=9053f9cb8f3fbe19c64eb5d171572266; isg=BOXl0KOH1agnMjF0I1VaKZer9KHfirLmjF40n-fKoZwr_gVwr3KphHOcjCItZbFs; _xm_umtoken=T29591B99AFC1450CD12CDC1ABCF11728DF3A9E2C4E646C9CF06656E227