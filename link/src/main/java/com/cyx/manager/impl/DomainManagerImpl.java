package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.enums.DomainTypeEnum;
import com.cyx.manager.DomainManager;
import com.cyx.mapper.DomainMapper;
import com.cyx.model.DomainDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DomainManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/21
 */
@Component
public class DomainManagerImpl implements DomainManager {

    @Autowired
    private DomainMapper domainMapper;

    @Override
    public DomainDO findById(Long id, Long accountNo) {
        return domainMapper.selectOne(new QueryWrapper<DomainDO>().eq("id", id).eq("account_no", accountNo));
    }

    @Override
    public DomainDO findByDomainTypeAndId(DomainTypeEnum domainTypeEnum, Long id) {
        return domainMapper.selectOne(new QueryWrapper<DomainDO>().eq("id", id).eq("domain_type", domainTypeEnum.name()));
    }

    @Override
    public int add(DomainDO domainDO) {
        return domainMapper.insert(domainDO);
    }

    @Override
    public List<DomainDO> listOfficialDomain() {
        return domainMapper.selectList(new QueryWrapper<DomainDO>().eq("domain_type", DomainTypeEnum.OFFICIAL.name()));
    }

    @Override
    public List<DomainDO> listCustomDomain(Long accountNo) {
        return domainMapper.selectList(new QueryWrapper<DomainDO>()
                .eq("domain_type", DomainTypeEnum.CUSTOM.name())
                .eq("account_no", accountNo));
    }
}
