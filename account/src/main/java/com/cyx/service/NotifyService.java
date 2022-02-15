package com.cyx.service;

import com.cyx.controller.request.SendCodeRequest;
import com.cyx.enums.SendCodeEnum;
import com.cyx.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description NotifyService
 * @Author cyx
 * @Date 2021/12/11
 **/
public interface NotifyService {
    /**
     * 获取图片验证
     *
     * @param request
     * @param response
     */
    void getCaptcha(HttpServletRequest request, HttpServletResponse response);

    /**
     * 发送验证码
     *
     * @param requestBody
     * @param request
     */
    JsonData sendCode(SendCodeRequest requestBody, HttpServletRequest request);

    /**
     * 验证码校验
     *
     * @param type
     * @param code
     * @param to
     * @return
     */
    boolean checkCode(SendCodeEnum type,String code,String to);
}
