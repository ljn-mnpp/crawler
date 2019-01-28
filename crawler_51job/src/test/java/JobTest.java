import com.ljn.crawlerJob.JobApplication;
import com.ljn.crawlerJob.crawler.Crawler_51job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author李胖胖
 * @Date 2019/1/27 12:07
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobApplication.class)
public class JobTest {

    @Autowired
    private Crawler_51job c;

    @Test
    public void test() throws InterruptedException {
        c.start(1);
        TimeUnit.SECONDS.sleep(30);
        c.stop();
    }

}
