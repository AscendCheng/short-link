package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.model.EventMessage;
import com.cyx.model.PageVo;
import com.cyx.model.ShortLinkDO;
import com.cyx.model.request.ShortLinkAddRequest;
import com.cyx.model.request.ShortLinkPageRequest;
import com.cyx.model.request.ShortLinkUpdateRequest;
import com.cyx.utils.JsonData;
import com.cyx.vo.ShortLinkVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cyx
 * @since 2022-02-15
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 解析短链.
     *
     * @param shortLinkCode 短链码
     * @return ShortLinkVO
     */
    ShortLinkVO parseShortLinkCode(String shortLinkCode);

    /**
     * 创建短链.
     *
     * @param request 请求参数.
     * @return JsonData
     */
    JsonData createShortLink(ShortLinkAddRequest request);

    /**
     * 处理新增短链消息.
     * @param eventMessage 消息
     * @return
     */
    boolean handleAddShortLink(EventMessage eventMessage);

    /**
     * 根据分组分页查询.
     *
     * @param request 请求
     * @return
     */
    PageVo pageShortLinkByGroupId(ShortLinkPageRequest request);

    /**
     * 删除短链.
     *
     * @param request 请求参数.
     * @return JsonData
     */
    JsonData deleteShortLink(ShortLinkAddRequest request);

    /**
     * 更新短链.
     *
     * @param request 请求参数.
     * @return JsonData
     */
    JsonData updateShortLink(ShortLinkUpdateRequest request);


    /**
     * 处理短链更新逻辑.
     *
     * @param eventMessage 消息
     * @return boolean
     */
    boolean handleUpdateShortLink(EventMessage eventMessage);

    /**
     * 处理短链删除逻辑.
     *
     * @param eventMessage 消息
     * @return boolean
     */
    boolean handleDeleteShortLink(EventMessage eventMessage);
}
