package com.example.rest.repository;

import com.example.rest.model.News;
import com.example.rest.web.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byCategoryName(newsFilter.getCategory()))
                .and(byAuthor(newsFilter.getUser()));

    }

    static Specification<News> byCategoryName(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("newsCategory").get("name"), category);
        };
    }

    static Specification<News> byAuthor(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }
}
