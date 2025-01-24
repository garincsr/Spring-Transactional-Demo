package com.enigmacamp.enigshop_challenge.model.dto.request;

import com.enigmacamp.enigshop_challenge.model.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> transactionDetails;
}
