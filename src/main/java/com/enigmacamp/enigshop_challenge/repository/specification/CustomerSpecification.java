package com.enigmacamp.enigshop_challenge.repository.specification;

import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {
    public static Specification<Customer> hasName(String keyword) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("fullName")),
                "%" + keyword.toLowerCase() + "%");
    }
}
