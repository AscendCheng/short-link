package com.cyx.service.impl;

import com.cyx.component.LoginInterceptor;
import com.cyx.manager.DomainManager;
import com.cyx.model.DomainDO;
import com.cyx.mapper.DomainMapper;
import com.cyx.service.DomainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.vo.DomainVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cyx
 * @since 2022-03-19
 */
@Service
public class DomainServiceImpl extends ServiceImpl<DomainMapper, DomainDO> implements DomainService {
    @Autowired
    private DomainManager domainManager;

    @Override
    public List<DomainVO> listAll() {
        List<DomainDO> official = domainManager.listOfficialDomain();
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        List<DomainDO> custom = domainManager.listCustomDomain(accountNo);
        official.addAll(custom);
        return official.stream().map(domainDO -> beanProcess(domainDO)).collect(Collectors.toList());
    }

    private DomainVO beanProcess(DomainDO domainDO) {
        DomainVO domainVO = new DomainVO();
        BeanUtils.copyProperties(domainDO, domainVO);
        return domainVO;
    }
}
