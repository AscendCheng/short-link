package com.cyx.manager;

import com.cyx.model.ShortLinkDO;

/**
 * ShortLinkManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/15
 */
public interface ShortLinkManager {
    /**
     * 添加短链码.
     *
     * @param shortLinkDo 参数
     * @return
     */
    int addShortLink(ShortLinkDO shortLinkDo);

    /**
     * 根据短链码查询.
     *
     * @param shortLinkCode 短链码
     * @return ShortLinkDO
     */
    ShortLinkDO findByShortLinkCode(String shortLinkCode);

    /**
     * 删除.
     *
     * @param shortLinkDO 参数
     * @return
     */
    int del(ShortLinkDO shortLinkDO);

    /**
     * 更新.
     *
     * @param shortLinkDo 参数
     * @return
     */
    int update(ShortLinkDO shortLinkDo);
}
