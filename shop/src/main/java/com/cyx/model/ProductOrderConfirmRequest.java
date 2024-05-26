package com.cyx.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ProductOrderConfirmRequest.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/3/12
 * @since 2024/3/12
 */
@Data
public class ProductOrderConfirmRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  订单类型
     */
    private Long productId;

    /**
     *  购买数量
     */
    private Integer buyNum;

    /**
     *  订单唯一标识
     */
    private String outTradeNo;

    /**
     *  NEW 未支付订单,
     PAY已经支付订单,
     CANCEL超时取消订单
     */
    private String state;

    /**
     *  订单总金额
     */
    private BigDecimal totalAmount;

    /**
     *  订单实际支付价格
     */
    private BigDecimal payAmount;

    /**
     *  支付类型，微信-银行-支付宝
     */
    private String payType;

    /**
     * 终端类型,手机、电脑.
     */
    private String clientType;

    /**
     * 防重令牌.
     */
    private String token;

    /**
     *  发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private String billType;

    /**
     *  发票抬头
     */
    private String billHeader;

    /**
     *  发票内容
     */
    private String billContent;

    /**
     *  发票收票人电话
     */
    private String billReceiverPhone;

    /**
     *  发票收票人邮箱
     */
    private String billReceiverEmail;
}
