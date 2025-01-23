package com.enigmacamp.enigshop_challenge.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRequest {
    private String query;
    private Integer page;
    private Integer size;
}
