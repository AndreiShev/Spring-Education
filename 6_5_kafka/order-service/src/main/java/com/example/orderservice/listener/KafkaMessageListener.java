package com.example.orderservice.listener;

import com.example.orderservice.model.AnotherEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    @KafkaListener(topics = "${app.kafka.kafkaOrderStatusTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "eventStatusKafkaListenerContainerFactory")
    public void listen(AnotherEvent anotherEvent,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", anotherEvent);
        log.info("Key: {}; partition: {}; Topic: {}; timestamp: {}", key, partition, topic, timestamp);
    }

}
