package com.github.aaric.achieve.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * RabbitMQ测试类
 *
 * @author Aaric, created on 2017-05-19T14:47.
 * @since 1.0-SNAPSHOT
 */
public class RabbitMQTest {

    public static final String QUEUE_NAME = "queue_hello";

    protected ConnectionFactory connectionFactory;
    protected Connection connection;
    protected Channel channel;

    @Before
    public void begin() throws Exception {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("linux6-1");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
    }

    @After
    public void end() throws Exception {
        channel.close();
        connection.close();
    }

    @Test
    public void testProducer() throws Exception {
        String message = "Hello World";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(Charset.defaultCharset()));
        System.out.println(" [x] Sent '" + message + "'");
    }

    @Test
    public void testConsumer() throws Exception {
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("properties: " + properties.getMessageId());
                String message = new String(body, Charset.defaultCharset());
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
