package com.cyx.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * ClientTypeEnum.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/3/12
 * @since 2024/3/12
 */
public enum ClientTypeEnum {

    APP,

    PC,

    H5;

    public static ClientTypeEnum getClientType(String clientType){
        if(StringUtils.isBlank(clientType)){
            return null;
        }
        try{
            return ClientTypeEnum.valueOf(clientType);
        }catch (Exception e){
            return null;
        }
    }
}
