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

    // 创建

    private String shortLinkEventExchange = "short_link.event.exchange";

    private String shortLinkAddLinkQueue = "short_link.add.link.queue";

    private String shortLinkAddMappingQueue = "short_link.add.mapping.queue";

    private String shortLinkAddRoutingKey = "short_link.add.link.mapping.routing.key";

    private String shortLinkAddLinkBindingKey = "short_link.add.link.*.routing.key";

    private String shortLinkAddMappingBindingKey = "short_link.add.*.mapping.routing.key";

    // 删除

    private String shortLinkDeleteLinkQueue = "short_link.delete.link.queue";

    private String shortLinkDeleteMappingQueue = "short_link.delete.mapping.queue";

    /**
     * 删除短链，用于生产者发送到队列.
     */
    private String shortLinkDeleteRoutingKey = "short_link.delete.link.mapping.routing.key";

    /**
     * 删除短链，用于消费者消费消息.
     */
    private String shortLinkDeleteLinkBindingKey = "short_link.delete.link.*.routing.key";


    private String shortLinkDeleteMappingBindingKey = "short_link.delete.*.mapping.routing.key";

    // 更新

    private String shortLinkUpdateLinkQueue = "short_link.update.link.queue";

    private String shortLinkUpdateMappingQueue = "short_link.update.mapping.queue";

    private String shortLinkUpdateRoutingKey = "short_link.update.link.mapping.routing.key";

    private String shortLinkUpdateLinkBindingKey = "short_link.update.link.*.routing.key";

    private String shortLinkUpdateMappingBindingKey = "short_link.update.*.mapping.routing.key";

    @Bean
    public Exchange shortLinkEventExchange() {
        return new TopicExchange(shortLinkEventExchange, true, false);
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

    @Bean
    public Binding shortLinkAddLinkApiBinding() {
        return new Binding(shortLinkAddLinkQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkAddLinkBindingKey, null);
    }

    @Bean
    public Binding shortLinkDeleteLinkApiBinding() {
        return new Binding(shortLinkDeleteLinkQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkDeleteLinkBindingKey, null);
    }

    @Bean
    public Binding shortLinkDeleteMappingApiBinding() {
        return new Binding(shortLinkDeleteMappingQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkDeleteMappingBindingKey, null);
    }

    @Bean
    public Queue shortLinkDeleteLinkQueue() {
        return new Queue(shortLinkDeleteLinkQueue, true, false, false);
    }

    @Bean
    public Queue shortLinkDeleteMappingQueue() {
        return new Queue(shortLinkDeleteMappingQueue, true, false, false);
    }

    @Bean
    public Binding shortLinkUpdateLinkApiBinding() {
        return new Binding(shortLinkUpdateLinkQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkUpdateLinkBindingKey, null);
    }

    @Bean
    public Binding shortLinkUpdateMappingApiBinding() {
        return new Binding(shortLinkUpdateMappingQueue, Binding.DestinationType.QUEUE,
                shortLinkEventExchange, shortLinkUpdateMappingBindingKey, null);
    }

    @Bean
    public Queue shortLinkUpdateLinkQueue() {
        return new Queue(shortLinkUpdateLinkQueue, true, false, false);
    }

    @Bean
    public Queue shortLinkUpdateMappingQueue() {
        return new Queue(shortLinkUpdateMappingQueue, true, false, false);
    }
}
