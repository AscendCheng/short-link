package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.manager.ProductManager;
import com.cyx.mapper.ProductMapper;
import com.cyx.model.ProductDO;
import com.cyx.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/16
 */
@Service
public class ProductManagerImpl implements ProductManager {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDO> list() {
        return productMapper.selectList(null);
    }

    @Override
    public ProductDO findDetailById(Long id) {
        return productMapper.selectOne(new QueryWrapper<ProductDO>().eq("id", id));
    }
}
