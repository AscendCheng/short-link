package com.cyx.controller;

import com.cyx.enums.ShortLinkStateEnum;
import com.cyx.service.ShortLinkService;
import com.cyx.utils.CommonUtil;
import com.cyx.vo.ShortLinkVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LinkApiController.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/15
 */
@Controller
@Slf4j
public class LinkApiController {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 301是永久重定向，302是临时重定向.
     * 
     *
     * @param shortLinkCode
     * @param request
     * @param response
     */
    @GetMapping(path = "/{shortLinkCode}")
    public void dispatch(@PathVariable("shortLinkCode") String shortLinkCode, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("短链码:{}", shortLinkCode);
            // 判断短链码是否合规
            if (!isLetterDigit(shortLinkCode)) {
                return;
            }
            ShortLinkVO shortLinkVO = shortLinkService.parseShortLinkCode(shortLinkCode);
            if (isVisitable(shortLinkVO)) {
                String originalUrl = CommonUtil.removeUrlPrefix(shortLinkVO.getOriginalUrl());
                response.setHeader("Location", originalUrl);
                // 302跳转
                response.setStatus(HttpStatus.FOUND.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 仅包括数字和字符.
     *
     * @param str 字符串
     * @return
     */
    private static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    /**
     * 是否可用.
     *
     * @param shortLinkVO 短链实体
     * @return
     */
    private static boolean isVisitable(ShortLinkVO shortLinkVO) {
        if (shortLinkVO != null && shortLinkVO.getExpired().getTime() > CommonUtil.getCurrentTimestamp()) {
            if (ShortLinkStateEnum.ACTIVE.name().equalsIgnoreCase(shortLinkVO.getState())) {
                return true;
            }
        } else if (shortLinkVO != null && shortLinkVO.getExpired().getTime() == -1) {
            if (ShortLinkStateEnum.ACTIVE.name().equalsIgnoreCase(shortLinkVO.getState())) {
                return true;
            }
        }
        return false;
    }
}
