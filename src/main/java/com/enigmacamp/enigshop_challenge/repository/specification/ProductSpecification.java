package com.enigmacamp.enigshop_challenge.repository.specification;

import com.enigmacamp.enigshop_challenge.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String keyword) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("name")),
                "%" + keyword.toLowerCase() + "%");
    }
}
