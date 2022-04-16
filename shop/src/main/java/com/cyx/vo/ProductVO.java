package com.cyx.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ProductVO.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/16
 */
@Data
public class ProductVO {
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 详情
     */
    private String detail;

    /**
     * 图片
     */
    private String img;

    /**
     * 产品层级：FIRST青铜、SECOND黄金、THIRD钻石
     */
    private String level;

    /**
     * 原价
     */
    private BigDecimal oldAmount;

    /**
     * 现价
     */
    private BigDecimal amount;

    /**
     * 工具类型 short_link、qrcode
     */
    private String pluginType;

    /**
     * 日次数：短链类型
     */
    private Integer dayTimes;

    /**
     * 总次数：活码才有
     */
    private Integer totalTimes;

    /**
     * 有效天数
     */
    private Integer validDay;

    private Date gmtModified;

    private Date gmtCreate;
}
