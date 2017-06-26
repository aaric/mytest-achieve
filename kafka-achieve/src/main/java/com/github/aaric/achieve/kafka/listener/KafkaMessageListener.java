package com.github.aaric.achieve.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * KafkaMessageListener
 *
 * @author Aaric, created on 2017-06-26T10:32.
 * @since 1.0-SNAPSHOT
 */
@Component
public class KafkaMessageListener implements MessageListener<String, String> {

    @KafkaListener(topics = {"testTopic"})
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        System.out.printf("Consumer: %s-%s\n", data.key(), data.value());
    }
}
