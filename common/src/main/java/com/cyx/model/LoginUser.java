package com.cyx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户编号
     */
    private Long accountNo;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 认证级别
     */
    private String auth;
}
