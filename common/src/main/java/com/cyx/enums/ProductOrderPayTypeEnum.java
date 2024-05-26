package com.cyx.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * ProductOrderPayTypeEnum.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/3/12
 * @since 2024/3/12
 */
public enum ProductOrderPayTypeEnum {
    WECHAT_PAY,

    ALI_PAY,

    OTHER;

    public static ProductOrderPayTypeEnum getPayType(String payType) {
        if (StringUtils.isBlank(payType)) {
            return null;
        }
        try {
            return ProductOrderPayTypeEnum.valueOf(payType);
        } catch (Exception e) {
            return null;
        }
    }
}
