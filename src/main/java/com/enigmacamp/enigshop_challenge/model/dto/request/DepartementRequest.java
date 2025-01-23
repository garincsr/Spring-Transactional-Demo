package com.enigmacamp.enigshop_challenge.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartementRequest {
    private Long id;
    private String name;
    private String code;
    private String description;
}
