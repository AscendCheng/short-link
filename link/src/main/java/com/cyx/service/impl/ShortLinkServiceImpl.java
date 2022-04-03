package com.cyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.component.LoginInterceptor;
import com.cyx.config.RabbitMqConfig;
import com.cyx.enums.EventMessageType;
import com.cyx.manager.ShortLinkManager;
import com.cyx.mapper.ShortLinkMapper;
import com.cyx.model.EventMessage;
import com.cyx.model.ShortLinkDO;
import com.cyx.model.request.ShortLinkAddRequest;
import com.cyx.service.ShortLinkService;
import com.cyx.utils.IdUtil;
import com.cyx.utils.JsonData;
import com.cyx.utils.JsonUtil;
import com.cyx.vo.ShortLinkVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cyx
 * @since 2022-02-15
 */
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Autowired
    private ShortLinkManager shortLinkManager;

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ShortLinkVO parseShortLinkCode(String shortLinkCode) {
        ShortLinkDO shortLinkDO = shortLinkManager.findByShortLinkCode(shortLinkCode);
        if (shortLinkDO == null) {
            return null;
        }
        ShortLinkVO shortLinkVO = new ShortLinkVO();
        BeanUtils.copyProperties(shortLinkDO, shortLinkVO);
        return shortLinkVO;
    }

    @Override
    public JsonData createShortLink(ShortLinkAddRequest request) {
        EventMessage eventMsg = EventMessage.builder().account(LoginInterceptor.threadLocal.get().getAccountNo())
                .content(JsonUtil.obj2Json(request))
                .messageId(IdUtil.geneSnowFlakeID().toString())
                .eventMessageType(EventMessageType.SHORT_LINK_ADD.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMqConfig.getShortLinkEventExchange(),
                rabbitMqConfig.getShortLinkAddRoutingKey(),
                eventMsg);

        return JsonData.buildSuccess();
    }

}
