package com.cyx.config;

import lombok.Data;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMqConfig.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/6/9
 * @since 2024/6/9
 */
@Configuration
@Data
public class RabbitMqConfig {

    /**
     * 交换机.
     */
    private String orderEventExchange = "order.event.exchange";

    /**
     * 延迟队列，不能被消费者监听.
     */
    private String orderCloseDelayQueue="order.close.delay.queue";

    /**
     * 关单队列，延迟队列过期后转发的队列，能够被消费者监听.
     */
    private String orderCloseQueue="order.close.queue";

    /**
     * 进入到延迟队列的routingKey.
     */
    private String orderCloseDelayRoutingKey="order.close.delay.routing.key";

    /**
     * 进入死信队列的routingKey.
     */
    private String orderCloseRoutingKey="order.close.routing.key";

    /**
     * 过期时间.
     */
    private Integer ttl = 60 * 1000;

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange orderEventExchange(){
        return new TopicExchange(orderEventExchange,true,false);
    }

    // 延迟队列.
    @Bean
    public Queue orderCloseDelayQueue(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",orderEventExchange);
        arguments.put("x-dead-letter-routing-key",orderCloseRoutingKey);
        arguments.put("x-message-ttl",ttl);
        return new Queue(orderCloseDelayQueue,true,false,false);
    }


}
