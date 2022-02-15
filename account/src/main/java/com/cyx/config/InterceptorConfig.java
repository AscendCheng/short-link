package com.cyx.config;

import com.cyx.component.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/account/*/**", "/api/traffic/*/**")
                .excludePathPatterns("/api/account/*/register", "/api/account/*/login", "/api/account/*/captcha", "/api/account/*/upload", "/api/account/*/sendCode");
    }
}
