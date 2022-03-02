package com.cyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.component.LoginInterceptor;
import com.cyx.manager.LinkGroupManager;
import com.cyx.mapper.LinkGroupMapper;
import com.cyx.model.LinkGroupDO;
import com.cyx.model.LinkGroupRequest;
import com.cyx.model.LinkGroupVO;
import com.cyx.model.LoginUser;
import com.cyx.service.LinkGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * LinkGroupServiceImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
@Service
public class LinkGroupServiceImpl extends ServiceImpl<LinkGroupMapper, LinkGroupDO> implements LinkGroupService {
    @Autowired
    private LinkGroupManager linkGroupManager;

    @Override
    public int add(LinkGroupRequest addRequest) {
        if (addRequest == null || addRequest.getTitle() == null) {
            return 0;
        }
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Long accountNo = loginUser.getAccountNo();

        LinkGroupDO linkGroupDO = new LinkGroupDO();
        linkGroupDO.setAccountNo(accountNo);
        linkGroupDO.setTitle(addRequest.getTitle());

        return linkGroupManager.add(linkGroupDO);
    }

    @Override
    public int delete(Long groupId) {
        if (groupId == null) {
            return 0;
        }
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Long accountNo = loginUser.getAccountNo();
        return linkGroupManager.delete(groupId, accountNo);
    }

    @Override
    public LinkGroupVO detail(Long groupId) {
        if (groupId == null) {
            return null;
        }
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Long accountNo = loginUser.getAccountNo();
        LinkGroupDO linkGroupDO = linkGroupManager.detail(groupId, accountNo);
        LinkGroupVO lingGroupVo = new LinkGroupVO();
        BeanUtils.copyProperties(linkGroupDO, lingGroupVo);
        return lingGroupVo;
    }

    @Override
    public List<LinkGroupVO> listAll() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Long accountNo = loginUser.getAccountNo();
        List<LinkGroupDO> linkGroupDoList = linkGroupManager.listAll(accountNo);

        List<LinkGroupVO> list = new ArrayList<>();
        for (LinkGroupDO linkGroupDO : linkGroupDoList) {
            LinkGroupVO linkGroupVO = new LinkGroupVO();
            BeanUtils.copyProperties(linkGroupDO, linkGroupVO);
        }
        return list;
    }

    @Override
    public int update(LinkGroupRequest updateRequest) {
        if (updateRequest == null || updateRequest.getTitle() == null) {
            return 0;
        }
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Long accountNo = loginUser.getAccountNo();
        int row = linkGroupManager.update(updateRequest.getId(),updateRequest.getTitle(),accountNo);
        return row;
    }

}
