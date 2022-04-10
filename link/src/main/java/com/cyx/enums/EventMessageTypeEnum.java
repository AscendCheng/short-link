package com.cyx.enums;

/**
 * EventMessageTypeEnum.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/1
 */
public enum EventMessageTypeEnum {
    /**
     * 新增短链.
     */
    SHORT_LINK_ADD,

    /**
     * C端.
     */
    SHORT_LINK_ADD_LINK,

    /**
     * B端.
     */
    SHORT_LINK_ADD_MAPPING,

    /**
     * 删除短链.
     */
    SHORT_LINK_DEL,

    /**
     * C端.
     */
    SHORT_LINK_DEL_LINK,

    /**
     * B端.
     */
    SHORT_LINK_DEL_MAPPING,

    /**
     * 更新短链.
     */
    SHORT_LINK_UPDATE,

    /**
     * C端.
     */
    SHORT_LINK_UPDATE_LINK,

    /**
     * B端.
     */
    SHORT_LINK_UPDATE_MAPPING;
}
