package biz;

import com.cyx.AccountApplication;
import com.cyx.component.SmsComponent;
import com.cyx.config.SmsConfig;
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
@SpringBootTest(classes = AccountApplication.class)
@Slf4j
public class SmsTest {
    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private SmsConfig smsConfig;

    @Test
    @Async
    public void sendTest() {
        smsComponent.send("", smsConfig.getTemplateId(), "");
    }
}
