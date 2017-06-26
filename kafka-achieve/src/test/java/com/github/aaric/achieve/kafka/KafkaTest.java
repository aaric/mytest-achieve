package com.github.aaric.achieve.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * KafkaTest
 *
 * @author Aaric, created on 2017-06-26T10:50.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class KafkaTest {

    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testProducer() {
        String topic = "testTopic";
        String data = "hello";
        System.out.printf("Producer: %s-%s\n", topic, data);
        kafkaTemplate.send(topic, data);
    }
}
