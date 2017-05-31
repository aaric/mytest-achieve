package com.github.aaric.achieve.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RocketMQProducer测试类
 *
 * @author Aaric, created on 2017-05-23T11:10.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RocketMQProducerTest {

    @Autowired
    protected RocketMQProducer rocketMQProducer;

    @Test
    public void testSend() throws Exception {
        SendResult result = rocketMQProducer.send("TopicTest1", //topic
                "TagA", //tag
                "OrderID001", //key
                "Hello MetaQ"); //body
        System.err.println(result);
    }
}
