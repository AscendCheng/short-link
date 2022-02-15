package com.cyx.component;

import com.cyx.enums.BizCodeEnum;
import com.cyx.model.LoginUser;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.JsonData;
import com.cyx.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是option请求直接返回
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        Claims claims = JwtUtil.checkJwt(token);
        if (claims == null) {
            CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        }
        Long accountNo = Long.parseLong(claims.get("account_no").toString());
        String headImg = claims.get("head_img").toString();
        String mail = claims.get("mail").toString();
        String username = claims.get("username").toString();
        String auth = claims.get("auth").toString();
        String phone = claims.get("phone").toString();
        LoginUser loginUser = LoginUser.builder().accountNo(accountNo).headImg(headImg).mail(mail).username(username).auth(auth).phone(phone).build();
        // 放在请求里 request.setAttribute("loginUser",loginUser);
        // threadLocal
        threadLocal.set(loginUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
