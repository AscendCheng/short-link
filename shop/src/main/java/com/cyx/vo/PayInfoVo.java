package com.cyx.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * PayInfoVo.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/3/12
 * @since 2024/3/12
 */
@Data
@Builder
public class PayInfoVo {
    private String outTradeNo;

    private BigDecimal payFee;

    private String payType;

    private String clientType;

    private String title;

    private String description;

    private long orderPayTimeOutMills;

    private Long accountNo;
}
