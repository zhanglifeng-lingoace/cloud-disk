package com.lingoace.edu.cloudDisk.mq;

import com.alibaba.fastjson.JSONObject;
import com.lingoace.edu.cloudDisk.config.mq.RabbitConfirmCallback;
import com.lingoace.edu.cloudDisk.config.mq.RabbitReturnCallback;

import com.lingoace.edu.common.entity.RabbitMqExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * 发送mq消息的方法
 *
 * @author zhanglifeng
 * @date 2020-06-29
 */
@Service
public class SendMqServiceImpl {

    private final static Logger LOGGER = LoggerFactory.getLogger(SendMqServiceImpl.class);
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitConfirmCallback());
        rabbitTemplate.setReturnCallback(new RabbitReturnCallback());
        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
    }

    /**
     * 发送消息
     *
     * @param mq         对应mq的exchange
     * @param jsonObject 需要传输的参数，通过json封装
     */
    public void sendMessage(RabbitMqExchange mq, JSONObject jsonObject) {
        rabbitTemplate.convertAndSend(
                mq.getExchangeName(),
                mq.getRoutingKey(),
                jsonObject.toJSONString(),
                new CorrelationData("unRouting-" + UUID.randomUUID().toString())
        );
        LOGGER.info("发送消息并返回消息确认,exchange:{},routingKey:{},message:{}", mq.getExchangeName(), mq.getRoutingKey(), jsonObject.toJSONString());
    }
}
