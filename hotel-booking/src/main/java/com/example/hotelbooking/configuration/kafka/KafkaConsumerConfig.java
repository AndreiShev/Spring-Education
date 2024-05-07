package com.example.hotelbooking.configuration.kafka;

import com.example.hotelbooking.model.AuthEvent;
import com.example.hotelbooking.model.BookingEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${app.kafka.kafkaMessageGroupId}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, AuthEvent> consumerAuthFactory() {
        Map<String, Object> props = getConfig();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(AuthEvent.class));
    }

    @Bean
    public ConsumerFactory<String, BookingEvent> consumerBookingFactory() {
        Map<String, Object> props = getConfig();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(BookingEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AuthEvent> eventAuthKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AuthEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerAuthFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingEvent> eventBookingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerBookingFactory());
        return factory;
    }

    private Map<String, Object> getConfig() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configs.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configs.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        configs.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return configs;
    }
}

