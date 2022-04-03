package com.cyx.manager;

import com.cyx.enums.DomainTypeEnum;
import com.cyx.model.DomainDO;

import java.util.List;

/**
 * DomainManager.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/21
 */
public interface DomainManager {
    /**
     * 查找详情.
     *
     * @param id
     * @param accountNo
     * @return
     */
    DomainDO findById(Long id, Long accountNo);

    /**
     * 根据类型和id查询.
     *
     * @param id
     * @param domainTypeEnum
     * @return
     */
    DomainDO findByDomainTypeAndId(DomainTypeEnum domainTypeEnum, Long id);

    /**
     * 新增.
     *
     * @param domainDO
     * @return
     */
    int add(DomainDO domainDO);

    /**
     * 查询官方域名.
     *
     * @return List
     */
    List<DomainDO> listOfficialDomain();

    /**
     * 查询自定义域名.
     *
     * @return List
     */
    List<DomainDO> listCustomDomain(Long accountNo);
}
