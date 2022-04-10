package com.cyx.model.request;

import lombok.Data;

/**
 * ShortLinkPageRequest.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/7
 */
@Data
public class ShortLinkPageRequest {
    /**
     * 第几页.
     */
    private int page;

    /**
     * 每页大小.
     */
    private int size;

    /**
     * 组Id.
     */
    private long groupId;
}
