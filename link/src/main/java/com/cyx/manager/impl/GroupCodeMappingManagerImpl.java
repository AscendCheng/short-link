package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.enums.ShortLinkStateEnum;
import com.cyx.manager.GroupCodeMappingManager;
import com.cyx.mapper.GroupCodeMappingMapper;
import com.cyx.model.GroupCodeMappingDO;
import com.cyx.model.PageVo;
import com.cyx.vo.GroupCodeMappingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GroupCodeMappingManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/19
 */
public class GroupCodeMappingManagerImpl implements GroupCodeMappingManager {
    @Autowired
    private GroupCodeMappingMapper groupCodeMappingMapper;

    @Override
    public GroupCodeMappingDO findByGroupIdAndMappingId(Long mappingId, Long accountNo, Long groupId) {
        GroupCodeMappingDO groupCodeMappingDO = groupCodeMappingMapper.selectOne(new QueryWrapper<GroupCodeMappingDO>().eq("id", mappingId)
                .eq("account_no", accountNo).eq("group_id", groupId));
        return groupCodeMappingDO;
    }

    @Override
    public int add(GroupCodeMappingDO groupCodeMappingDO) {
        return groupCodeMappingMapper.insert(groupCodeMappingDO);
    }

    @Override
    public int delete(String shortLinkCode, Long accountNo, Long groupId) {
        int rows = groupCodeMappingMapper.update(null,
                new UpdateWrapper<GroupCodeMappingDO>()
                        .eq("code", shortLinkCode)
                        .eq("account_no", accountNo)
                        .eq("group_id", groupId).set("del", 1));
        return rows;
    }

    @Override
    public PageVo pageShortLinkByGroupId(Integer page, Integer size, Long accountNo, Long groupId) {
        Page<GroupCodeMappingDO> pageInfo = new Page<>(page, size);
        Page<GroupCodeMappingDO> pageResult = groupCodeMappingMapper.selectPage(pageInfo, new QueryWrapper<GroupCodeMappingDO>()
                .eq("account_no", accountNo)
                .eq("group_id", groupId));
        Map<String, Object> pageMap = new HashMap<>(3 * 2);

        List<GroupCodeMappingVO> voList = pageResult.getRecords().stream().map(recordDo -> processGroupCodeMappingDO(recordDo)).collect(Collectors.toList());
        return new PageVo(voList, pageResult.getCurrent(), pageResult.getTotal());
    }

    @Override
    public int updateState(Long accountNo, Long groupId, String shortLinkCode, ShortLinkStateEnum shortLinkStateEnum) {
        int rows = groupCodeMappingMapper.update(null,
                new UpdateWrapper<GroupCodeMappingDO>()
                        .eq("code", shortLinkCode)
                        .eq("account_no", accountNo)
                        .eq("group_id", groupId).set("state", shortLinkStateEnum.name()));
        return rows;
    }

    private GroupCodeMappingVO processGroupCodeMappingDO(GroupCodeMappingDO groupCodeMappingDO) {
        GroupCodeMappingVO groupCodeMappingVO = new GroupCodeMappingVO();
        BeanUtils.copyProperties(groupCodeMappingDO, groupCodeMappingVO);
        return groupCodeMappingVO;
    }
}
