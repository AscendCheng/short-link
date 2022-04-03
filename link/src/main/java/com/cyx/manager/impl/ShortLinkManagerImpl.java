package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.manager.ShortLinkManager;
import com.cyx.mapper.ShortLinkMapper;
import com.cyx.model.ShortLinkDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ShortLinkManagerImpl.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/15
 */
@Component
public class ShortLinkManagerImpl implements ShortLinkManager {
    @Autowired
    private ShortLinkMapper shortLinkMapper;

    @Override
    public int addShortLink(ShortLinkDO shortLinkDo) {
        return shortLinkMapper.insert(shortLinkDo);
    }

    @Override
    public ShortLinkDO findByShortLinkCode(String shortLinkCode) {
        return shortLinkMapper.selectOne(new QueryWrapper<ShortLinkDO>().eq("code", shortLinkCode));
    }

    @Override
    public int del(String shortLinkCode, Long accountNo) {
        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setDel(1);
        return shortLinkMapper.update(shortLinkDO,
                new QueryWrapper<ShortLinkDO>().eq("code", shortLinkCode).eq("accout_no", accountNo));
    }
}
