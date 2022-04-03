package com.cyx.manager;

import com.cyx.enums.ShortLinkStateEnum;
import com.cyx.model.GroupCodeMappingDO;
import com.cyx.model.PageVo;

/**
 * GroupCodeMappingManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/19
 */
public interface GroupCodeMappingManager {

    /**
     * 查找详情.
     *
     * @param mappingId
     * @param accountNo
     * @param groupId
     * @return
     */
    GroupCodeMappingDO findByGroupIdAndMappingId(Long mappingId, Long accountNo, Long groupId);

    /**
     * 新增.
     *
     * @param groupCodeMappingDO
     * @return
     */
    int add(GroupCodeMappingDO groupCodeMappingDO);

    /**
     * 根据短链码删除.
     *
     * @param shortLinkCode
     * @param accountNo
     * @param groupId
     * @return
     */
    int delete(String shortLinkCode, Long accountNo, Long groupId);

    /**
     * 分页查找.
     *
     * @param page
     * @param size
     * @param accountNo
     * @param groupId
     * @return
     */
    PageVo pageShortLinkByGroupId(Integer page, Integer size, Long accountNo, Long groupId);

    /**
     * 更新状态。
     *
     * @param accountNo
     * @param groupId
     * @param shortLinkCode
     * @param shortLinkStateEnum
     * @return
     */
    int updateState(Long accountNo, Long groupId, String shortLinkCode, ShortLinkStateEnum shortLinkStateEnum);
}
