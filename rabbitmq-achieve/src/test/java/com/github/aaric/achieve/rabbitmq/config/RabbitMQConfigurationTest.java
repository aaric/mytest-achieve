package com.github.aaric.achieve.rabbitmq.config;

import com.github.aaric.achieve.rabbitmq.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RabbitMQConfiguration测试类
 *
 * @author Aaric, created on 2017-05-22T12:02.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMQConfigurationTest {

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @Test
    public void testSend() throws Exception {
        rabbitTemplate.convertAndSend("hello");
    }

}
