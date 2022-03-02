package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.manager.LinkGroupManager;
import com.cyx.mapper.LinkGroupMapper;
import com.cyx.model.LinkGroupDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * LinkGroupManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
@Service
public class LinkGroupManagerImpl implements LinkGroupManager {
    @Autowired
    private LinkGroupMapper linkGroupMapper;

    @Override
    public int add(LinkGroupDO linkGroupDO) {
        return linkGroupMapper.insert(linkGroupDO);
    }

    @Override
    public int delete(Long groupId, Long accountNo) {
        return linkGroupMapper.delete(new QueryWrapper<LinkGroupDO>().eq("id", groupId).eq("account_no", accountNo));
    }

    @Override
    public LinkGroupDO detail(Long groupId, Long accountNo) {
        return linkGroupMapper.selectOne(new QueryWrapper<LinkGroupDO>().eq("id", groupId).eq("account_no", accountNo));
    }

    @Override
    public List<LinkGroupDO> listAll(Long accountNo) {
        return linkGroupMapper.selectList(new QueryWrapper<LinkGroupDO>().eq("account_no", accountNo));
    }

    @Override
    public int update(Long groupId, String title, Long accountNo) {
        LinkGroupDO linkGroupDO = linkGroupMapper.selectOne(new QueryWrapper<LinkGroupDO>().eq("id", groupId).eq("account_no", accountNo));
        linkGroupDO.setTitle(title);
        linkGroupDO.setGmtModified(new Date());
        return linkGroupMapper.updateById(linkGroupDO);
    }
}
