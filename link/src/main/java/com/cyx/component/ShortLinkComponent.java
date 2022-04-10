package com.cyx.component;

import com.cyx.config.ShardingDBConfig;
import com.cyx.config.ShardingTableConfig;
import com.cyx.utils.CommonUtil;
import org.springframework.stereotype.Component;

@Component
public class ShortLinkComponent {
    public String createShortLink(String originalUrl) {
        long hash = CommonUtil.murmurHash32(originalUrl);
        String code = CommonUtil.encodeToBase62(hash);
        String shortLinkCode = ShardingDBConfig.getRandomDBPrefix(code) + code + ShardingTableConfig.getRandomTableSuffix(code);
        return shortLinkCode;
    }
}
