package com.cyx.controller;


import com.cyx.controller.request.UserLoginRequest;
import com.cyx.controller.request.UserRegisterRequest;
import com.cyx.enums.BizCodeEnum;
import com.cyx.service.AccountService;
import com.cyx.service.FileService;
import com.cyx.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cyx
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/api/account/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public JsonData uploadUserImage(@RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserImage(file);
        return result != null ? JsonData.buildSuccess(result) : JsonData.buildError(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL.getMessage());
    }

    @PostMapping("/register")
    private JsonData register(@RequestBody UserRegisterRequest request) {
        return accountService.register(request);
    }

    @PostMapping("/login")
    private JsonData login(@RequestBody UserLoginRequest request){
        return accountService.login(request);
    }
}

