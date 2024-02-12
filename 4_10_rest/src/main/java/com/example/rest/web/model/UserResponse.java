package com.example.rest.web.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private List<NewsResponseForResponseList> news = new ArrayList<>();
}
