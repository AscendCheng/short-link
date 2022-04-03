package com.cyx.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMqConfig.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Configuration
@Data
public class RabbitMqConfig {

    private String shortLinkEventExchange = "short_link.event.exchange";

    private String shortLinkAddLinkQueue = "short_link.add.link.queue";

    private String shortLinkAddMappingQueue = "short_link.add.mapping.queue";

    private String shortLinkAddRoutingKey = "short_link.add.link.mapping.routing.key";

    private String shortLinkAddLinkBindingKey = "short_link.add.link.*.routing.key";

    private String shortLinkAddMappingBindingKey = "short_link.add.*.mapping.routing.key";

    @Bean
    public Exchange shortLinkEventExchange() {
        return new TopicExchange(shortLinkEventExchange, true, false);
    }

    @Bean
    public Binding shortLinkAddLinkApiBinding() {
        return new Binding(shortLinkAddLinkQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkAddLinkBindingKey, null);
    }

    @Bean
    public Binding shortLinkAddMappingApiBinding() {
        return new Binding(shortLinkAddMappingQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkAddMappingBindingKey, null);
    }

    @Bean
    public Queue shortLinkAddLinkQueue() {
        return new Queue(shortLinkAddLinkQueue, true, false, false);
    }

    @Bean
    public Queue shortLinkAddMappingQueue() {
        return new Queue(shortLinkAddMappingQueue, true, false, false);
    }
}
