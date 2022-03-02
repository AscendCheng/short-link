package com.cyx.manager;

import com.cyx.model.LinkGroupDO;

import java.util.List;

/**
 * LinkGroupManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
public interface LinkGroupManager {

    int add(LinkGroupDO linkGroupDO);

    int delete(Long groupId,Long accountNo);

    LinkGroupDO detail(Long groupId, Long accountNo);

    List<LinkGroupDO> listAll(Long accountNo);

    int update(Long groupId,String title, Long accountNo);
}
