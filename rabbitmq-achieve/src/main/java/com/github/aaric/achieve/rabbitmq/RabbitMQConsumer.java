package com.github.aaric.achieve.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ与Spring集成：接收消息
 *
 * @author Aaric, created on 2017-05-22T10:36.
 * @since 1.0-SNAPSHOT
 */
@Component
public class RabbitMQConsumer {

    /**
     * Receiving a message
     *
     * @param content msg
     */
    @RabbitListener(queues = RabbitMQProducer.QUEUE_NAME)
    public void processMessage(String content) {
        System.out.println("Consumer: " + content);
    }

}
