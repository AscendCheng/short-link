package com.cyx.listener;

import com.cyx.model.EventMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ShortLinkAddLinkMqListener.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Component
@Slf4j
@RabbitListener(queuesToDeclare = {@Queue("short_link.error.queue")})
public class ShortLinkAddLinkErrorMqListener {
    @RabbitHandler
    public void shortLinkHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息shortLinkError,message内容：{}", eventMessage);
    }
}
