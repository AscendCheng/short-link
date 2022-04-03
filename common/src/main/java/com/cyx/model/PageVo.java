package com.cyx.model;

import lombok.Data;

/**
 * PageVo.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/19
 */
@Data
public class PageVo {
    private long currentPage;

    private long total;

    private Object items;

    public PageVo(Object items, long currentPage, long total) {
        this.currentPage = currentPage;
        this.total = total;
        this.items = items;
    }
}
