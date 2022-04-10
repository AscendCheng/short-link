package com.cyx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMqErrorConfig.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/4/3
 */
@Configuration
@Slf4j
public class RabbitMqErrorConfig {
    /**
     * 异常交换机.
     */
    private String shortLinkErrorExchange = "short_link.error.exchange";

    /**
     * 异常队列.
     */
    private String shortLinkErrorQueue = "short_link.error.queue";

    /**
     * 异常队列bindingKey.
     */
    private String shortLinkErrorRoutingKey = "short_link.error.routing.key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public TopicExchange errorTopExchange() {
        return new TopicExchange(shortLinkErrorExchange, true, false);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(shortLinkErrorQueue, true);
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(errorTopExchange()).with(shortLinkErrorRoutingKey);
    }

    /**
     * 消息消费异常到达一定次数后，用特定的bindingKey转发到特定的交换机中.
     *
     * @return MessageRecoverer
     */
    @Bean
    public MessageRecoverer messageRecoverer() {
        return new RepublishMessageRecoverer(rabbitTemplate, shortLinkErrorExchange, shortLinkErrorRoutingKey);
    }
}
