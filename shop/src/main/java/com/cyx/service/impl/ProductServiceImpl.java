package com.cyx.service.impl;

import com.cyx.manager.ProductManager;
import com.cyx.model.ProductDO;
import com.cyx.service.ProductService;
import com.cyx.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductManager productManager;

    @Override
    public List<ProductVO> list() {
        List<ProductDO> productDOList = productManager.list();
        List<ProductVO> resultList = productDOList.stream().map(this::beanProcess).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public ProductVO detail(Long id) {
        ProductDO productDO = productManager.findDetailById(id);
        return this.beanProcess(productDO);
    }

    private ProductVO beanProcess(ProductDO productDO){
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productDO,productVO);
        return productVO;
    }
}
