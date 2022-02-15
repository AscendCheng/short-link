package com.cyx.component;

import com.cyx.utils.CommonUtil;
import org.springframework.stereotype.Component;

@Component
public class ShortLinkComponent {
    public String createShortLink(String originalUrl) {
        long hash = CommonUtil.murmurHash32(originalUrl);
        return CommonUtil.encodeToBase62(hash);
    }
}
