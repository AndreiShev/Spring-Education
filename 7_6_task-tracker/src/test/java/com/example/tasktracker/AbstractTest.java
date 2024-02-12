package com.example.tasktracker;


import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractTest {

    protected static String FIRST_ITEM_ID = UUID.randomUUID().toString();
    protected static String SECOND_ITEM_ID = UUID.randomUUID().toString();

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.8")
            .withReuse(true);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository.saveAll(
                List.of(
                        new User(FIRST_ITEM_ID, "user 1", "user_1@mail.com"),
                        new User(SECOND_ITEM_ID, "user 2", "user_2@mail.com")
                )
        ).collectList().block();
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll().block();
    }
}
