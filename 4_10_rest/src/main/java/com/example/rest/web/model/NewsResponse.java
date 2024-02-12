package com.example.rest.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private Long  id;

    private String description;

    private List<CommentResponse> commentResponseList = new ArrayList<>();

}
