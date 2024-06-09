package com.cyx.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RedissonConfiguration.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/6/9
 * @since 2024/6/9
 */
@Component
public class RedissonConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    // 单机模式
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setPassword(password).setDatabase(0).setAddress("redis://" + host + ":" + port);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
