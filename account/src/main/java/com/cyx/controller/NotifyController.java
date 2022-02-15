package com.cyx.controller;

import com.cyx.controller.request.SendCodeRequest;
import com.cyx.service.NotifyService;
import com.cyx.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description NotifyController
 * @Author cyx
 * @Date 2021/12/11
 **/
@RestController
@RequestMapping("/api/account/v1")
@Slf4j
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        notifyService.getCaptcha(request, response);
    }

    @PostMapping("/sendCode")
    public JsonData sendCode(@RequestBody SendCodeRequest requestBody, HttpServletRequest request) {
        return notifyService.sendCode(requestBody, request);
    }
}
