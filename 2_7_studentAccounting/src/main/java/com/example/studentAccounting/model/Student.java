package com.example.studentAccounting.model;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    public Student(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return lastName+ " " + firstName + ", age - " + age + ", id: " + id;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
