package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        return shortLinkMapper.selectOne(new QueryWrapper<ShortLinkDO>().eq("code", shortLinkCode).eq("del",0));
    }

    @Override
    public int del(ShortLinkDO shortLinkDO) {
        return shortLinkMapper.update(shortLinkDO,
                new UpdateWrapper<ShortLinkDO>().eq("code", shortLinkDO.getCode())
                        .eq("account_no", shortLinkDO.getAccountNo())
                        .set("del", 1));
    }

    @Override
    public int update(ShortLinkDO shortLinkDo) {
        int rows = shortLinkMapper.update(null,new UpdateWrapper<ShortLinkDO>()
                .eq("code",shortLinkDo.getCode())
                .eq("del",0)
                .eq("account_no", shortLinkDo.getAccountNo())
                .set("title",shortLinkDo.getTitle())
                .set("domain",shortLinkDo.getDomain())
        );
        return rows;
    }
}
