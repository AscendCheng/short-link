package com.cyx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description SmsConfig
 * @Author cyx
 * @Date 2021/12/7
 **/
@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
public class SmsConfig {
    private String appCode;
    private String templateId;
    private String appSecrete;
}
