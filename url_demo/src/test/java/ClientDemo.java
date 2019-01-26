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

}
