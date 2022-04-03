package com.cyx.model.request;

import lombok.Data;

import java.util.Date;

/**
 * ShortLinkAddRequest.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/1
 */
@Data
public class ShortLinkAddRequest {
    /**
     * 组.
     */
    private Long groupId;

    /**
     * 标题.
     */
    private String title;

    /**
     * 原始路径.
     */
    private String originalUrl;

    /**
     * 域名id.
     */
    private Long domainId;

    /**
     * 域名类型.
     */
    private String domainType;

    /**
     * 过期时间.
     */
    private Date expired;
}
