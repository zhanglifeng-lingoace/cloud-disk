package com.lingoace.edu.cloudDisk.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.lingoace.edu.common.entity.RabbitMqQueue.RABBIT_QUEUE_TEST001;

@Component
public class MqConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(MqConsumer.class);

    @RabbitListener(queues = RABBIT_QUEUE_TEST001)
    public void handleHomeworkStudentFinish(String message, Message arg0, Channel channel) {
        LOGGER.info("mq的入参信息:" + message);
        try {
            JSONObject jsonObject = JSONObject.parseObject(message);

        } catch (Exception e) {
            LOGGER.error("mq消费发生异常*********异常信息：{}", e);
        }
        LOGGER.info("------------------ RABBIT_QUEUE_TEST001 消费结束--------------------------");
    }

}
