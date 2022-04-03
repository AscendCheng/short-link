package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.model.ShortLinkDO;
import com.cyx.model.request.ShortLinkAddRequest;
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
}
