package com.cyx.model.request;

import lombok.Data;

/**
 * ShortLinkUpdateRequest.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/10
 */
@Data
public class ShortLinkUpdateRequest {
    /**
     * 组id.
     */
    private Long groupId;

    /**
     * 映射id.
     */
    private Long mappingId;

    /**
     * 更新短链码.
     */
    private String code;

    /**
     * 标题.
     */
    private String title;

    /**
     * 域名Id.
     */
    private Long domainId;

    /**
     * 域名类型.
     */
    private String domainType;
}
