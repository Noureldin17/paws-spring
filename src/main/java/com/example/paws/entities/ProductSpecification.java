package com.example.paws.entities;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import com.example.paws.entities.Product;

import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasPetTypes(List<String> petTypeNames) {
        return (root, query, criteriaBuilder) -> {
            if (petTypeNames == null || petTypeNames.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            // Join with the PetType entity
            Join<Product, PetType> petTypeJoin = root.join("petType"); // Ensure "petType" matches the field name in your Product entity
            return petTypeJoin.get("name").in(petTypeNames);
        };
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction(); // No filtering
            } else if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }
}

