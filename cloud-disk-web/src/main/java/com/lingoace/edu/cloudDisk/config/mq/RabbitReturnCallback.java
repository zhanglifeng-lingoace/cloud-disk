package com.lingoace.edu.cloudDisk.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 发生异常时的消息返回提醒
 * 需要开启
 * # 开启发送失败退回
 * spring.rabbitmq.publisher-returns=true
 **/
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitReturnCallback.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        LOGGER.info("消息主体: {}", message);
        LOGGER.info("回复编码: {}", replyCode);
        LOGGER.info("回复内容: {}", replyText);
        LOGGER.info("交换器: {}", exchange);
        LOGGER.info("路由键: {}", routingKey);
    }
}