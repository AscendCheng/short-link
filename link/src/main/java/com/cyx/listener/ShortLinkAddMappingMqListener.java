package com.cyx.listener;

import com.cyx.enums.BizCodeEnum;
import com.cyx.exception.BizException;
import com.cyx.model.EventMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ShortLinkAddMappingMqListener.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Component
@Slf4j
@RabbitListener(queues = "short_link.add.mapping.queue")
public class ShortLinkAddMappingMqListener {

    @RabbitHandler
    public void shortLinkHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息ShortLinkAddMappingMqListener,message内容：{}",message);
        long tag = message.getMessageProperties().getDeliveryTag();
        try{

        }catch (Exception e){
            log.error("监听到消息ShortLinkAddMappingMqListener消费异常",e.getMessage());
            throw new BizException(BizCodeEnum.MQ_CONSUMER_EXCEPTION);
        }
        log.error("监听到消息ShortLinkAddMappingMqListener消费成功,{}",eventMessage);
        channel.basicAck(tag,false);
    }
}