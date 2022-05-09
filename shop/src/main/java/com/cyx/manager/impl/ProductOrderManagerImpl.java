package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.manager.ProductOrderManager;
import com.cyx.mapper.ProductOrderMapper;
import com.cyx.model.PageVo;
import com.cyx.model.ProductOrderDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductOrderManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/16
 */
@Service
public class ProductOrderManagerImpl implements ProductOrderManager {
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Override
    public int add(ProductOrderDO productOrderDO) {
        return productOrderMapper.insert(productOrderDO);
    }

    @Override
    public ProductOrderDO findByOutTradeNoAndAccountNo(String outTradeNo, String accountNo) {
        ProductOrderDO productOrderDO = productOrderMapper.selectOne(new QueryWrapper<>(new ProductOrderDO())
                .eq("out_trade_no", outTradeNo)
                .eq("account_no", accountNo));
        return productOrderDO;
    }

    @Override
    public int updateOrderPayState(String outTradeNo, String accountNo, String oldPayState, String newPayState) {
        productOrderMapper.update(null, new UpdateWrapper<ProductOrderDO>()
                .eq("out_trade_no", outTradeNo)
                .eq("account_no", accountNo)
                .eq("del", "0")
                .eq("state", oldPayState)
                .set("state", newPayState)
        );
        return 0;
    }

    @Override
    public PageVo page(int pageNum, int pageSize, String accountNo, String payState) {
        Page<ProductOrderDO> page = new Page<>(pageNum, pageSize);
        IPage<ProductOrderDO> iPage;
        if (StringUtils.isBlank(payState)) {
            iPage = productOrderMapper.selectPage(page, new QueryWrapper<>(new ProductOrderDO()).eq("account_no", accountNo).eq("del", "0"));
        } else {
            iPage = productOrderMapper.selectPage(page, new QueryWrapper<>(new ProductOrderDO()).eq("account_no", accountNo).eq("del", "0").eq("state", payState));
        }
        return new PageVo(iPage.getRecords(), iPage.getCurrent(), iPage.getTotal());
    }
}
