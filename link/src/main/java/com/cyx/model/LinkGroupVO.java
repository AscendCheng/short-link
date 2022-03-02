package com.cyx.model;

import lombok.Data;

import java.util.Date;

/**
 * LinkGroupVO.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
@Data
public class LinkGroupVO {
    private Long id;

    /**
     * 组名
     */
    private String title;

    /**
     * 账号唯一编号
     */
    private Long accountNo;

    private Date gmtCreate;

    private Date gmtModified;
}
