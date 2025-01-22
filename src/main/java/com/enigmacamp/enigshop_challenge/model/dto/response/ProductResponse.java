package com.enigmacamp.enigshop_challenge.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
}
