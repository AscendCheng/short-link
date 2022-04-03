package com.cyx.controller;


import com.cyx.service.DomainService;
import com.cyx.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyx
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/api/domain/v1")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @GetMapping("list")
    public JsonData list(){
        return JsonData.buildSuccess(domainService.listAll());
    }
}

