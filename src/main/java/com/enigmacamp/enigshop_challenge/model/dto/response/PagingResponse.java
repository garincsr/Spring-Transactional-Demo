package com.enigmacamp.enigshop_challenge.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingResponse {
    private Integer totalPage;
    private Long totalElement;
    private Integer page;
    private Integer size;
    private Boolean hashNext;
    private Boolean hashPrevious;
}
