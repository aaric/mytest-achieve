package com.github.aaric.achieve.rabbitmq.config;

import com.github.aaric.achieve.rabbitmq.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * AmqpConfig测试类
 *
 * @author Aaric, Created on 2017-05-17T14:45.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AmqpConfigurationTest {

    @Autowired
    private AmqpConfiguration amqpConfiguration;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConnectionFactory() throws  Exception {
        System.err.println(amqpConfiguration.getAddress());
    }

    @Test
    public void testRabbitTemplate() throws Exception {
        System.err.println(rabbitTemplate.getConnectionFactory().getHost());
    }

    @Test
    public void testSendMsg() throws Exception {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(AmqpConfiguration.EXCHANGE, AmqpConfiguration.ROUTINGKEY, "hello", correlationData);
    }

}
