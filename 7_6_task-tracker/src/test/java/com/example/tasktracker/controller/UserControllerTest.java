package com.example.tasktracker.controller;

import com.example.tasktracker.AbstractTest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserControllerTest extends AbstractTest {

    @Test
    public void whenGetAllItems_thenReturnListOfItemsFromDatabase() {
        var expectedData = List.of(
                new User(FIRST_ITEM_ID, "user 1", "user_1@mail.com"),
                new User(SECOND_ITEM_ID, "user 2", "user_2@mail.com")
        );

        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .contains(expectedData.toArray(User[]::new));

    }

    @Test
    public void whenGetUserById_thenReturnUserById() {
        var expectedData = new User(SECOND_ITEM_ID, "user 2", "user_2@mail.com");

        webTestClient.get().uri("/api/users/{id}", SECOND_ITEM_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(expectedData);
    }

}
