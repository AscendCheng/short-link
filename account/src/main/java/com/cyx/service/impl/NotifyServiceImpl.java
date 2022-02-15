package com.cyx.service.impl;

import com.cyx.component.SmsComponent;
import com.cyx.config.SmsConfig;
import com.cyx.controller.request.SendCodeRequest;
import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.SendCodeEnum;
import com.cyx.service.NotifyService;
import com.cyx.utils.CheckUtil;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.JsonData;
import com.google.code.kaptcha.Producer;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description NotifyServiceImpl
 * @Author cyx
 * @Date 2021/12/11
 **/
@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {
    /**
     * 验证码过期时间
     */
    private static final long CODE_EXPIRED_TIME = 60 * 1000;

    private static final long CACHE_CAPTCHA_EXPIRED_TIME = 1000 * 60 * 10;

    private static final String CACHE_CAPTCHA_KEY_FORMAT = "account-service:captcha:%s";

    private static final String CACHE_CODE_KEY_FORMAT = "account-service:code:%s:%s";


    @Autowired
    private Producer captchaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = captchaProducer.createText();
        log.info("验证内容:{}", captchaText);
        BufferedImage image = captchaProducer.createImage(captchaText);
        redisTemplate.opsForValue().set(getCaptchaCatchKey(request), captchaText,
                CACHE_CAPTCHA_EXPIRED_TIME, TimeUnit.MILLISECONDS);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("获取流出错", e);
        }
    }

    @Override
    public JsonData sendCode(SendCodeRequest requestBody, HttpServletRequest request) {
        if (requestBody.getCaptcha() == null || requestBody.getTo() == null) {
            return JsonData.buildError(BizCodeEnum.CODE_ERROR.getMessage());
        }
        String key = getCaptchaCatchKey(request);
        String captcha = redisTemplate.opsForValue().get(key);
        if (captcha != null && captcha.equals(requestBody.getCaptcha())) {
            redisTemplate.delete(key);
            return this.sendCodeMsg(SendCodeEnum.USER_REGISTER, requestBody.getTo());
        } else {
            return JsonData.buildError(BizCodeEnum.CODE_ERROR.getMessage());
        }
    }

    @Override
    public boolean checkCode(SendCodeEnum type, String code, String to) {
        String cacheCodeKey = String.format(CACHE_CODE_KEY_FORMAT, type.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheCodeKey);
        if (StringUtil.isNotBlank(cacheValue)) {
            String cacheCode = cacheValue.split("_")[0];
            if (cacheCode.equals(code)) {
                return true;
            }
        }
        return false;
    }


    private JsonData sendCodeMsg(SendCodeEnum type, String to) {
        String cacheCodeKey = String.format(CACHE_CODE_KEY_FORMAT, type.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheCodeKey);

        if (StringUtil.isNotBlank(cacheValue)) {
            Long ttl = Long.parseLong(cacheValue.split("_")[1]);
            if (System.currentTimeMillis() - ttl < CODE_EXPIRED_TIME) {
                return JsonData.buildError(BizCodeEnum.CODE_LIMITED.getMessage());
            }
        }
        String code = CommonUtil.getRandomCode(6);
        String value = code.concat("_") + (CommonUtil.getCurrentTimestamp());
        redisTemplate.opsForValue().set(cacheCodeKey, value, CODE_EXPIRED_TIME, TimeUnit.MILLISECONDS);
        if (CheckUtil.isPhone(to)) {
            smsComponent.send(to, smsConfig.getTemplateId(), code);
        }
        return JsonData.buildSuccess();
    }


    private String getCaptchaCatchKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        // 浏览器标识符
        String agent = request.getHeader("User-Agent");
        String userAddress = ip + agent;
        String key = String.format(CACHE_CAPTCHA_KEY_FORMAT, CommonUtil.MD5(userAddress));
        log.info("获取验证码：地址:{}，key:{}", userAddress, key);
        return key;
    }
}
