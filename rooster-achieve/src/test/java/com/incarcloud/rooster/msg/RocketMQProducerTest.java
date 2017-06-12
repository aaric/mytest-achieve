package com.incarcloud.rooster.msg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * RocketMQProducer测试类
 *
 * @author Aaric, created on 2017-05-27T10:50.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RocketMQProducerTest {

    @Autowired
    protected RocketMQProducer rocketMQProducer;

    @Test
    public void testSend() throws Exception {
        rocketMQProducer.send("rooster", "org", UUID.randomUUID().toString(), "中文测试");
    }
}
