package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.model.PageVo;
import com.cyx.model.ProductOrderConfirmRequest;
import com.cyx.model.ProductOrderDO;
import com.cyx.utils.JsonData;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    /**
     * 分页接口.
     *
     * @param page  页数
     * @param size  页面数据大小
     * @param state 状态
     * @return 分页结果
     */
    PageVo page(int page, int size, String state);

    /**
     * 查询订单状态.
     *
     * @param outTradeNo 订单编码
     * @return
     */
    String queryProductOrderState(String outTradeNo);

    JsonData confirmOrder(ProductOrderConfirmRequest request);
}
