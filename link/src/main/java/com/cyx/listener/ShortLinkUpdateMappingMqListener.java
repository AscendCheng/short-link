package com.cyx.listener;

import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.EventMessageTypeEnum;
import com.cyx.exception.BizException;
import com.cyx.model.EventMessage;
import com.cyx.service.ShortLinkService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ShortLinkUpdateMappingMqListener.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Component
@Slf4j
@RabbitListener(queues = "short_link.update.mapping.queue")
public class ShortLinkUpdateMappingMqListener {
    @Autowired
    private ShortLinkService shortLinkService;

    @RabbitHandler
    public void shortLinkHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息ShortLinkUpdateMappingMqListener,message内容：{}", eventMessage);
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            eventMessage.setEventMessageType(EventMessageTypeEnum.SHORT_LINK_UPDATE_MAPPING.name());
            shortLinkService.handleUpdateShortLink(eventMessage);
        } catch (Exception e) {
            log.error("监听到消息ShortLinkUpdateMappingMqListener消费异常", e);
            throw new BizException(BizCodeEnum.MQ_CONSUMER_EXCEPTION);
        }
        log.info("监听到消息ShortLinkUpdateMappingMqListener消费成功,{}", eventMessage);
        channel.basicAck(tag, false);
    }
}