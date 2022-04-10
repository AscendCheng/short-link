package com.cyx.model.request;

import lombok.Data;

/**
 * ShortLinkDelRequest.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/10
 */
@Data
public class ShortLinkDelRequest {
    /**
     * 组id.
     */
    private Long groupId;

    /**
     * 映射id.
     */
    private Long mappingId;

    /**
     * 删除短链码.
     */
    private String code;
}
