package com.cyx.controller;


import com.cyx.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyx
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/api/productOrder/v1")
public class ProductOrderController {
    @Autowired
    private ProductOrderService productOrderService;

}

