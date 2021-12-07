package com.cyx.exception;

import com.cyx.enums.BizCodeEnum;
import lombok.Data;

/**
 * @Description BizException
 * @Author cyx
 * @Date 2021/12/4
 **/
@Data
public class BizException extends RuntimeException{
    private int code;
    private String msg;

    public BizException(BizCodeEnum bizCodeEnum) {
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }
}
