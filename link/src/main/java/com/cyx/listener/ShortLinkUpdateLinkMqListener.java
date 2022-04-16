package com.cyx.listener;

import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.EventMessageTypeEnum;
import com.cyx.exception.BizException;
import com.cyx.model.EventMessage;
import com.cyx.service.ShortLinkService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ShortLinkUpdateLinkMqListener.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Component
@Slf4j
@RabbitListener(queuesToDeclare = {@Queue("short_link.update.link.queue")})
public class ShortLinkUpdateLinkMqListener {
    @Autowired
    private ShortLinkService shortLinkService;

    @RabbitHandler
    public void shortLinkHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息ShortLinkUpdateLinkMqListener,message内容：{}", eventMessage);
        try {
            eventMessage.setEventMessageType(EventMessageTypeEnum.SHORT_LINK_UPDATE_LINK.name());
            shortLinkService.handleUpdateShortLink(eventMessage);
        } catch (Exception e) {
            log.error("监听到消息ShortLinkUpdateLinkMqListener消费异常", e);
            throw new BizException(BizCodeEnum.MQ_CONSUMER_EXCEPTION);
        }
        log.info("监听到消息ShortLinkUpdateLinkMqListener消费成功,{}", eventMessage);
    }
}
