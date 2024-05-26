package com.cyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.component.LoginInterceptor;
import com.cyx.constant.TimeConstant;
import com.cyx.enums.BillTypeEnum;
import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.ProductOrderStateEnum;
import com.cyx.exception.BizException;
import com.cyx.manager.ProductManager;
import com.cyx.manager.ProductOrderManager;
import com.cyx.mapper.ProductOrderMapper;
import com.cyx.model.LoginUser;
import com.cyx.model.PageVo;
import com.cyx.model.ProductDO;
import com.cyx.model.ProductOrderConfirmRequest;
import com.cyx.model.ProductOrderDO;
import com.cyx.service.ProductOrderService;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.JsonData;
import com.cyx.utils.JsonUtil;
import com.cyx.vo.PayInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
@Service
@Slf4j
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

    @Autowired
    private ProductOrderManager productOrderManager;

    @Autowired
    private ProductManager productManager;

    @Override
    public PageVo page(int page, int size, String state) {
        LoginUser user = LoginInterceptor.threadLocal.get();
        return productOrderManager.page(page, size, user.getAccountNo(), state);
    }

    @Override
    public String queryProductOrderState(String outTradeNo) {
        LoginUser user = LoginInterceptor.threadLocal.get();
        ProductOrderDO product = productOrderManager.findByOutTradeNoAndAccountNo(outTradeNo, user.getAccountNo());
        return product != null ? product.getState() : null;
    }

    @Override
    public JsonData confirmOrder(ProductOrderConfirmRequest request) {
        LoginUser user = LoginInterceptor.threadLocal.get();
        String orderTradeNo = CommonUtil.getStringNumRandom(32);
        ProductDO productDO = productManager.findDetailById(request.getProductId());

        this.checkPrice(productDO, request);

        ProductOrderDO productOrderDO = this.saveProductOrder(request, user, orderTradeNo, productDO);

        PayInfoVo payInfoVo = PayInfoVo.builder()
                .outTradeNo(orderTradeNo)
                .clientType(request.getClientType())
                .payType(request.getPayType())
                .title(productDO.getTitle())
                .description(productDO.getDetail())
                .payFee(request.getPayAmount())
                .orderPayTimeOutMills(TimeConstant.ORDER_PAY_TIMEOUT_MILL)
                .build();


        return null;
    }

    private ProductOrderDO saveProductOrder(ProductOrderConfirmRequest request, LoginUser user, String orderTradeNo, ProductDO productDO) {
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setAccountNo(user.getAccountNo());
        productOrderDO.setNickname(user.getUsername());

        productOrderDO.setProductId(productOrderDO.getProductId());
        productOrderDO.setProductTitle(productDO.getTitle());
        productOrderDO.setProductSnapshot(JsonUtil.obj2Json(productDO));
        productOrderDO.setProductAmount(productDO.getAmount());

        productOrderDO.setBuyNum(request.getBuyNum());
        productOrderDO.setOutTradeNo(orderTradeNo);
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setDel(0);

        productOrderDO.setBillType(BillTypeEnum.valueOf(request.getBillType()).name());
        productOrderDO.setBillHeader(request.getBillHeader());
        productOrderDO.setBillContent(request.getBillContent());
        productOrderDO.setBillReceiverPhone(request.getBillReceiverPhone());
        productOrderDO.setBillReceiverEmail(request.getBillReceiverEmail());

        productOrderDO.setPayAmount(request.getPayAmount());
        productOrderDO.setTotalAmount(request.getTotalAmount());

        productOrderDO.setState(ProductOrderStateEnum.valueOf(request.getState()).name());

        return productOrderDO;
    }

    private void checkPrice(ProductDO productDO, ProductOrderConfirmRequest request) {

        // 计算价格
        BigDecimal bizPrice = new BigDecimal(request.getBuyNum()).multiply(productDO.getAmount());
        this.useCoupon(productDO, request, bizPrice);

        // 验证价格
        if (request.getPayAmount().compareTo(bizPrice) != 0) {
            log.error("验证价格失败:{}", request);
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_PRICE_FAIL);
        }
    }

    private void useCoupon(ProductDO productDO, ProductOrderConfirmRequest request, BigDecimal bizPrice) {
        return;
    }

}
