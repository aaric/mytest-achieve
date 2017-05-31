package com.github.aaric.achieve.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ与Spring集成：发送消息
 *
 * @author Aaric, created on 2017-05-22T10:15.
 * @since 1.0-SNAPSHOT
 */
@Component
public class RabbitMQProducer {

    /**
     * Queue name.
     */
    public static final String QUEUE_NAME = "queue_hello";

    /**
     * RabbitMQ admin.
     */
    private AmqpAdmin amqpAdmin;
    /**
     * RabbitMQ template.
     */
    private AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitMQProducer(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * Sending a message
     *
     * @param content msg
     * @throws Exception
     */
    public void send(String content) throws Exception {
        amqpAdmin.declareQueue(new Queue(QUEUE_NAME, false,false,false,null));
        amqpTemplate.convertAndSend(QUEUE_NAME, content);
        System.out.println("Producer: " + content);
    }
}
