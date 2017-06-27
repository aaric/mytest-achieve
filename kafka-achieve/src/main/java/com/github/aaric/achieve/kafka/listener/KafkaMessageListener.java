package com.github.aaric.achieve.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * KafkaMessageListener
 *
 * @author Aaric, created on 2017-06-26T10:32.
 * @since 1.0-SNAPSHOT
 */
@Component
public class KafkaMessageListener {

    @KafkaListener(topics = {"DefaultTopic", "testTopic"})
    public void processMessage(ConsumerRecord<String, String> data) {
        System.out.printf("Consumer: [%s#%s]-%s\n", data.topic(), data.key(), data.value());
    }
}
