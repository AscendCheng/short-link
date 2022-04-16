package com.cyx.service;

import com.cyx.vo.ProductVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
public interface ProductService {
    List<ProductVO> list();

    ProductVO detail(Long id);
}
