package com.enigmacamp.enigshop_challenge.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String id;
    private ProductResponse productResponse;
    private Long productPrice;
    private Integer qty;
}
