package com.enigmacamp.enigshop_challenge.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartementResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
}
