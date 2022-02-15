package com.cyx.controller.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String headImg;
    private String phone;
    private String pwd;
    private String mail;
    private String username;
    private String code;
}
