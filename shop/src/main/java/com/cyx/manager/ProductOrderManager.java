package com.cyx.manager;

import com.cyx.model.PageVo;
import com.cyx.model.ProductOrderDO;

/**
 * ProductOrderManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/16
 */
public interface ProductOrderManager {
    /**
     * 添加订单.
     *
     * @param productOrderDO 订单
     * @return
     */
    int add(ProductOrderDO productOrderDO);

    /**
     * 根据订单号和账号查询订单.
     *
     * @param outTradeNo 订单号
     * @param accountNo  账号
     * @return
     */
    ProductOrderDO findByOutTradeNoAndAccountNo(String outTradeNo, String accountNo);

    /**
     * 更新订单.
     *
     * @param outTradeNo  订单号
     * @param accountNo   账号
     * @param oldPayState 原支付状态
     * @param newPayState 新支付状态
     * @return
     */
    int updateOrderPayState(String outTradeNo, String accountNo, String oldPayState, String newPayState);

    PageVo page(int pageNum, int pageSize, String accountNo, String payState);
}
