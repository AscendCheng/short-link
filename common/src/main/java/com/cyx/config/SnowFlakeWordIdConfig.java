package com.cyx.config;

import com.cyx.enums.BizCodeEnum;
import com.cyx.exception.BizException;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class SnowFlakeWordIdConfig {
    /**
     * 动态指定sharding jdbc 的雪花算法中的属性work.id属性
     * 通过调用System.setProperty()的方式实现,可用容器的 id 或者机器标识位
     * workId最大值 1L << 10，就是1024，即 0<= workId < 1024
     * {@link SnowflakeShardingKeyGenerator#getWorkerId()}
     *
     */
    static {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            String addressIp = ip4.getHostAddress();
            int workerId = (Math.abs(addressIp.hashCode()) % 1024);
            System.out.println(workerId);
            System.setProperty("workerId", workerId + "");
        } catch (UnknownHostException e) {
            throw new BizException(BizCodeEnum.OPS_NETWORK_ADDRESS_ERROR);
        }
    }
}
