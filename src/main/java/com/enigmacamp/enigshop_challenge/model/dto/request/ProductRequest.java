package com.enigmacamp.enigshop_challenge.model.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
}
