package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.model.DomainDO;
import com.cyx.vo.DomainVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyx
 * @since 2022-03-19
 */
public interface DomainService extends IService<DomainDO> {

    /**
     * 查询全部.
     *
     * @return List
     */
    List<DomainVO> listAll();
}
