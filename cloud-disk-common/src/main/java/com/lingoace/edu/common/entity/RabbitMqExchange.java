package com.lingoace.edu.common.entity;


import java.util.Arrays;
import java.util.List;

import static com.lingoace.edu.common.entity.RabbitMqQueue.RABBIT_QUEUE_TEST001;

/**
 * rabbit mq 配置枚举
 * ********  exchange指定为direct!  ***********
 * ********  exchange指定为direct!  ***********
 * ********  exchange指定为direct!  ***********
 *
 *[exchange 的类型有四种：direct,fanout,headers,topic
 *
 * Direct：简述为完全匹配才能转发消息，获得消息！
 * 处理路由键，需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键为 “green”，则只有路由键为“green”的消息才被转发，不会转发路由键为"red"，只会转发路由键为"green"。
 *
 * Fanout：简述为类似于广播，群发。接收到的消息，会分发给所有绑定的队列。
 * 不处理路由键。你只需要简单的将队列绑定到交换机上。一个发送到该类型交换机的消息都会被广播到与该交换机绑定的所有队列上。
 *
 *Topic：简述为 模糊匹配，符合表达式的都能转发，消费。
 * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”只能匹配一个词。
 *
 *Headers：这个不常用，不用看。
 * ]
 * @author zhanglifeng
 * @date 2020-06-29
 */
public enum RabbitMqExchange {


    RABBIT_MQ_EXCHANGE_TEST001("test001_exchange", "test001_routing_key", RABBIT_QUEUE_TEST001),

    ;

    /**
     * rabbitmq exchange
     */
    private final String exchangeName;

    /**
     * rabbitmq routingkey
     */
    private final String routingKey;

    /**
     * rabbitmq queue
     */
    private final String queueName;

    RabbitMqExchange(String exchangeName, String routingKey, String queueName) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getQueueName(){
        return queueName;
    }

    public static List<RabbitMqExchange> getAll() {
        return Arrays.asList(RabbitMqExchange.values());
    }
}