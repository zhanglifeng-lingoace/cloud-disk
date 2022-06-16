package com.lingoace.edu.cloudDisk.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送成功的回调
 * 需要开启
 * # 开启发送确认
 * 等效于配置 spring.rabbitmq.publisher-confirm-type=CORRELATED
 **/
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConfirmCallback.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //correlationData必须在发送的时候设置，否则将会使null，如下：
        //CorrelationData correlationData = new CorrelationData("1");
        //rabbitTemplate.convertAndSend("OrderDispathExchange","kk","{\"msgId\":1,\"data\":\"测试\"}",correlationData);
        LOGGER.info("消息唯一标识: {}", correlationData);
        LOGGER.info("确认状态: {}", ack);
        LOGGER.info("造成原因: {}", cause);
    }
}