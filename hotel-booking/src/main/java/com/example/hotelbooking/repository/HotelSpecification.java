package com.example.hotelbooking.repository;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.web.model.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification.where(byId(filter.getId()))
                .and(byTitle(filter.getTitle()))
                .and(byTitleOfTheAd(filter.getTitleOfTheAd()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistanceFromCityCenter(filter.getDistanceFromCityCenter()))
                .and(byRating(filter.getRating()))
                .and(byNumberOfRatings(filter.getNumberOfRatings()));
    }

    static Specification<Hotel> byId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("title"), title);
        };
    }

    static Specification<Hotel> byTitleOfTheAd(String titleOfTheAd) {
        return (root, query, criteriaBuilder) -> {
            if (titleOfTheAd == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("titleOfTheAd"), titleOfTheAd);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null) {
                return null;
            }

            return criteriaBuilder.like(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistanceFromCityCenter(Double distanceFromCityCenter) {
        return (root, query, criteriaBuilder) -> {
            if (distanceFromCityCenter == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromCityCenter"), distanceFromCityCenter);
        };
    }


    static Specification<Hotel> byRating(Double rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byNumberOfRatings(Long numberOfRatings) {
        return (root, query, criteriaBuilder) -> {
            if (numberOfRatings == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfRatings"), numberOfRatings);
        };
    }
}
