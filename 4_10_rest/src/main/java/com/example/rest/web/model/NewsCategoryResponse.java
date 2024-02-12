package com.example.rest.web.model;

import com.example.rest.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryResponse {
    private Integer id;

    private String name;

    private String description;

    private List<NewsResponseForResponseList> news = new ArrayList<>();
}
