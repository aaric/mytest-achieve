package com.github.aaric.achieve.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RabbitMQProducer测试类
 *
 * @author Aaric, created on 2017-05-22T12:03.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMQProducerTest {

    @Autowired
    protected RabbitMQProducer rabbitMQProducer;

    @Test
    public void testSend() throws Exception {
        rabbitMQProducer.send("hello");
    }

}
