import com.cyx.component.ShortLinkComponent;
import com.cyx.LinkApplication;
import com.cyx.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description SmsTest
 * @Author cyx
 * @Date 2021/12/7
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApplication.class)
@Slf4j
public class LinkTest {
    @Autowired
    private ShortLinkComponent shortLinkComponent;

    @Test
    @Async
    public void murMurHashTest() {
        for (int i = 0; i < 5; i++) {
            String originalUrl = "https://cherrycola.cn"+ CommonUtil.generateUUID() + CommonUtil.getStringNumRandom(6);
            System.out.println(shortLinkComponent.createShortLink(originalUrl));
        }
    }
}
