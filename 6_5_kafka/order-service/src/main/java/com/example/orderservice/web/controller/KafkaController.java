package com.example.orderservice.web.controller;


import com.example.orderservice.model.OrderEvent;
import com.example.orderservice.web.model.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaOrderTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody @Valid Order order) {
        kafkaTemplate.send(topicName, new OrderEvent(order.getProduct(), order.getQuantity()));

        return ResponseEntity.ok("Message sent to kafka");
    }
}
