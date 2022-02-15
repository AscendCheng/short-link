package biz;

import com.cyx.AccountApplication;
import com.cyx.component.SmsComponent;
import com.cyx.config.SmsConfig;
import com.cyx.mapper.TrafficMapper;
import com.cyx.model.TrafficDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

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
    private TrafficMapper trafficMapper;

    @Autowired
    private SmsConfig smsConfig;

    @Test
    @Async
    public void sendTest() {
        smsComponent.send("", smsConfig.getTemplateId(), "");
    }


    @Test
    @Async
    public void testShardingSave(){
        Random random = new Random();
        for (int i = 0 ;i<10;i++){
            TrafficDO trafficDO = new TrafficDO();
            trafficDO.setAccountNo(Long.valueOf(random.nextInt(100)));
            trafficMapper.insert(trafficDO);
        }
    }
}
