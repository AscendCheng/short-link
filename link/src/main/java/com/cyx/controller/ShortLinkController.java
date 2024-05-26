package com.cyx.controller;


import com.cyx.model.request.ShortLinkAddRequest;
import com.cyx.model.request.ShortLinkPageRequest;
import com.cyx.model.request.ShortLinkUpdateRequest;
import com.cyx.service.ShortLinkService;
import com.cyx.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
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

    @PostMapping("/add")
    public JsonData createShortLink(@RequestBody ShortLinkAddRequest request) {
        return shortLinkService.createShortLink(request);
    }

    /**
     * 分页查找短链
     *
     * @return
     */
    @RequestMapping("/page")
    public JsonData pageShortLinkByGroupId(@RequestBody ShortLinkPageRequest request) {
        return JsonData.buildSuccess(shortLinkService.pageShortLinkByGroupId(request));
    }

    @PostMapping("/delete")
    public JsonData deleteShortLink(@RequestBody ShortLinkAddRequest request) {
        return shortLinkService.deleteShortLink(request);
    }

    @PostMapping("/update")
    public JsonData updateShortLink(@RequestBody ShortLinkUpdateRequest request) {
        return shortLinkService.updateShortLink(request);
    }
}

