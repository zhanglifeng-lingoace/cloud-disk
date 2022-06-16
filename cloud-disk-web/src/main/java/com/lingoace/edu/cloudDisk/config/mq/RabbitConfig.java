package com.lingoace.edu.cloudDisk.config.mq;

import com.google.common.collect.Lists;
import com.lingoace.edu.common.entity.RabbitMqExchange;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * rabbitmq配置
 *
 * @author zhanglifeng
 * @date 2020-06-29
 */
@Configuration
public class RabbitConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        autoDeclareRabbitConfig(connectionFactory);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 自动声明配置
     *
     * @param connectionFactory
     */
    private void autoDeclareRabbitConfig(ConnectionFactory connectionFactory) {
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true);

        try {
            Optional.ofNullable(RabbitMqExchange.getAll())
                    .orElse(Lists.newArrayList())
                    .stream()
                    .forEach(rabbitMqExchange -> {
                                try {
                                    // 声明交换机
                                    /**
                                     *  * ********  exchange指定为direct!  ***********
                                     *  * ********  exchange指定为direct!  ***********
                                     *  * ********  exchange指定为direct!  ***********
                                     */
                                    channel.exchangeDeclare(rabbitMqExchange.getExchangeName(), "direct", true, false, null);

                                    // 声明队列
                                    channel.queueDeclare(rabbitMqExchange.getQueueName(), true, false, false, null);

                                    // 绑定队列到交换机
                                    channel.queueBind(rabbitMqExchange.getQueueName(), rabbitMqExchange.getExchangeName(), rabbitMqExchange.getRoutingKey());
                                } catch (Exception e) {
                                    LOGGER.error("自动声明失败, rabbitMqExchange:{}, Exception:{}", rabbitMqExchange, e);
                                }
                            }
                    );
        } catch (Exception e) {
            LOGGER.error("RabbitConfig.autoDeclareRabbitConfig Exception: ", e);
        } finally {
            try {
                connection.close();
                channel.close();
            } catch (Exception f) {
                LOGGER.error("RabbitConfig.autoDeclareRabbitConfig.close Exception: ", f);
            }
        }
    }
}