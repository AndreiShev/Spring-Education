package com.example.contacts.model;

import lombok.Data;

@Data
public class Contact {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
