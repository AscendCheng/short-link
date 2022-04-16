package com.cyx.manager;

import com.cyx.model.ProductDO;

import java.util.List;

/**
 * ProductManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/16
 */
public interface ProductManager {
    List<ProductDO> list();

    ProductDO findDetailById(Long id);
}
