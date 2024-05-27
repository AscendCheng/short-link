package com.cyx.controller;


import com.cyx.annotation.RepeatSubmit;
import com.cyx.component.LoginInterceptor;
import com.cyx.constant.RedisKey;
import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.ClientTypeEnum;
import com.cyx.enums.ProductOrderPayTypeEnum;
import com.cyx.model.ProductOrderConfirmRequest;
import com.cyx.service.ProductOrderService;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/api/productOrder/v1")
@Slf4j
public class ProductOrderController {
    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/page")
    @RepeatSubmit(limitType = RepeatSubmit.Type.TOKEN)
    public JsonData page(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         @RequestParam(value = "state", required = false) String state) {
        return JsonData.buildSuccess(productOrderService.page(page, size, state));
    }

    @GetMapping("/query_state")
    public JsonData queryState(@RequestParam(value = "out_trade_no", required = true) String outTradeNo) {
        String state = productOrderService.queryProductOrderState(outTradeNo);
        return StringUtils.isBlank(state) ? JsonData.buildResult(BizCodeEnum.PAY_ORDER_NOT_EXIST) : JsonData.buildSuccess(state);
    }

    @PostMapping("/confirm")
    public void confirmOrder(ProductOrderConfirmRequest request, HttpServletResponse response) {
        JsonData jsonData = productOrderService.confirmOrder(request);

        if (jsonData.getCode() == 0) {
            ProductOrderPayTypeEnum payType = ProductOrderPayTypeEnum.getPayType(request.getPayType());
            ClientTypeEnum clientType = ClientTypeEnum.getClientType(request.getClientType());
            if (payType == ProductOrderPayTypeEnum.ALI_PAY) {
                if (clientType == ClientTypeEnum.PC) {
                    CommonUtil.sendHtmlMessage(response, jsonData);
                }
            } else if (payType == ProductOrderPayTypeEnum.WECHAT_PAY) {

            }


        } else {
            log.error("创建订单失败{}", jsonData);
            CommonUtil.sendJsonMessage(response, jsonData);
        }
    }

    @GetMapping("token")
    public JsonData getOrderToken() {
        long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();

        String token = CommonUtil.getStringNumRandom(32);

        String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY, accountNo, token);

        redisTemplate.opsForValue().set(key, String.valueOf(Thread.currentThread().getId()), 30, TimeUnit.MINUTES);

        return JsonData.buildSuccess(token);
    }
}

