package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.model.LinkGroupDO;
import com.cyx.model.request.LinkGroupRequest;
import com.cyx.model.LinkGroupVO;

import java.util.List;

/**
 * LinkGroupService.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
public interface LinkGroupService extends IService<LinkGroupDO> {
    /**
     * 添加分组.
     *
     * @param addRequest 请求
     * @return int
     */
    int add(LinkGroupRequest addRequest);

    /**
     * 删除分组.
     *
     * @param groupId 分组id
     * @return int
     */
    int delete(Long groupId);

    /**
     * 查询详情.
     *
     * @param groupId 分组id
     * @return
     */
    LinkGroupVO detail(Long groupId);

    /**
     * 查询所有.
     *
     * @return
     */
   List<LinkGroupVO> listAll();

    /**
     * 更新.
     *
     * @param updateRequest 请求
     * @return int
     */
    int update(LinkGroupRequest updateRequest);
}
