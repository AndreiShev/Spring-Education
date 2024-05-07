package com.example.hotelbooking.listener;

import com.example.hotelbooking.model.AuthEvent;
import com.example.hotelbooking.model.BookingEvent;
import com.example.hotelbooking.repository.AuthRepository;
import com.example.hotelbooking.repository.BookingEventRepository;
import lombok.AllArgsConstructor;
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
    private final AuthRepository authRepository;
    private final BookingEventRepository bookingEventRepository;

    @KafkaListener(topics = "${app.kafka.kafkaAuthTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "eventAuthKafkaListenerContainerFactory")
    public void listenAuth(AuthEvent authEvent,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        authRepository.save(authEvent);
        log.info("Received message: {}", authEvent);
        log.info("Key: {}; partition: {}; Topic: {}; timestamp: {}", key, partition, topic, timestamp);
    }


    @KafkaListener(topics = "${app.kafka.kafkaBookingTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "eventBookingKafkaListenerContainerFactory")
    public void listenBooking(BookingEvent bookingEvent,
                              @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                              @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        bookingEventRepository.save(bookingEvent);
        log.info("Received message: {}", bookingEvent);
        log.info("Key: {}; partition: {}; Topic: {}; timestamp: {}", key, partition, topic, timestamp);
    }

}
