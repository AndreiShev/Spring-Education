package com.example.rest.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseForResponseList {
    private Long  id;

    private String description;

    private Integer numberOfComments;
}
