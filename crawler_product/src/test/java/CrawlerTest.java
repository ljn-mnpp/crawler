import com.ljn.crawler_jd.Application;
import com.ljn.crawler_jd.crawler.Crawler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author李胖胖
 * @Date 2019/1/26 20:22
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CrawlerTest {
    @Autowired
    private Crawler crawler;

    @Test
    public void t(){
        crawler.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
