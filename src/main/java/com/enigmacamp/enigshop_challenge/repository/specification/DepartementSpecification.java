package com.enigmacamp.enigshop_challenge.repository.specification;

import com.enigmacamp.enigshop_challenge.model.entity.Departement;
import org.springframework.data.jpa.domain.Specification;

public class DepartementSpecification {
    public static Specification<Departement> hasName(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(
                    builder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Departement> hasCode(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(
                    builder.lower(root.get("code")),
                    keyword.toLowerCase()
            );
        };
    }
}
