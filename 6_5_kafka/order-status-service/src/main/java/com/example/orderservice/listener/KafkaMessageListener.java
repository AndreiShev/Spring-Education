package com.example.orderservice.listener;

import com.example.orderservice.model.AnotherEvent;
import com.example.orderservice.model.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaMessageListener {

    @Value(value = "${app.kafka.kafkaOrderStatusTopic}")
    private final String orderStatusTopic = "";

    private final KafkaTemplate<String, AnotherEvent> kafkaTemplate;

    @KafkaListener(topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "orderEventKafkaListenerContainerFactory")
    public void listen(OrderEvent orderEvent) {
        kafkaTemplate.send("order-status-service", new AnotherEvent("CREATED", Instant.now()));
    }
}
