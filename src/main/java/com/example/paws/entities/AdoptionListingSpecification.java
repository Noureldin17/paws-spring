package com.example.paws.entities;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AdoptionListingSpecification {
    public static Specification<AdoptionListing> hasPetTypes(List<String> petTypeNames) {
        return (root, query, criteriaBuilder) -> {
            if (petTypeNames == null || petTypeNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<AdoptionListing, PetType> petTypeJoin = root.join("petType");
            return petTypeJoin.get("name").in(petTypeNames);
        };
    }

    public static Specification<AdoptionListing> hasMinAge(Integer minAge) {
        return (root, query, criteriaBuilder) -> {
            if (minAge == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge);
        };
    }

    public static Specification<AdoptionListing> hasMaxAge(Integer maxAge) {
        return (root, query, criteriaBuilder) -> {
            if (maxAge == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge);
        };
    }

    public static Specification<AdoptionListing> isAvailable() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), "AVAILABLE");
    }
}

