package com.cyx.vo;

import lombok.Data;

import java.util.Date;

/**
 * DomainVO.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/21
 */
@Data
public class DomainVO {
    private Long id;

    /**
     * 用户自己绑定的域名
     */
    private Long accountNo;

    /**
     * 域名类型，自建custom, 官方official
     */
    private String domainType;

    private String value;

    /**
     * 0是默认，1是禁用
     */
    private Integer del;

    private Date gmtCreate;

    private Date gmtModified;
}
