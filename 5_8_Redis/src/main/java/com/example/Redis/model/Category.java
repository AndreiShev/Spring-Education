package com.example.Redis.model;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

@Entity
@Table(name = "category")
@Data
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @OneToOne(mappedBy = "category", fetch = FetchType.EAGER)
    private Book book;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

}
