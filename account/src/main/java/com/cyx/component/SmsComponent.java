package com.cyx.component;

import com.cyx.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Description SmsComponent
 * @Author cyx
 * @Date 2021/12/7
 **/
@Component
@Slf4j
public class SmsComponent {
    private final String send_url = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsConfig smsConfig;

    public void send(String mobile, String templateId, String value) {
        String url = String.format(send_url, mobile, templateId, value);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "APPCODE " + smsConfig.getAppCode());
        HttpEntity entity = new HttpEntity(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("【发送短信】成功,url:{},body:{}", url, responseEntity.getBody());
        } else {
            log.error("【发送短信】失败,url:{},body:{}", url, responseEntity.getBody());
        }
    }
}
