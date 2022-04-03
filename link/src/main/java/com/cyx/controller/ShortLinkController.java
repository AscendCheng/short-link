package com.cyx.controller;


import com.cyx.model.request.ShortLinkAddRequest;
import com.cyx.service.ShortLinkService;
import com.cyx.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyx
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/api/link/v1")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @PostMapping("/create")
    public JsonData createShortLink(@RequestBody ShortLinkAddRequest request){
        return shortLinkService.createShortLink(request);
    }
}

