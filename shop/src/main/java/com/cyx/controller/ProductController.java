package com.cyx.controller;


import com.cyx.service.ProductService;
import com.cyx.utils.JsonData;
import com.cyx.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/api/product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public JsonData list(){
        List<ProductVO> list = productService.list();
        return JsonData.buildSuccess(list);
    }

    @GetMapping("/detail/{id}")
    public JsonData detail(@PathVariable("id") Long id){
        ProductVO productVO = productService.detail(id);
        return JsonData.buildSuccess(productVO);
    }
}

