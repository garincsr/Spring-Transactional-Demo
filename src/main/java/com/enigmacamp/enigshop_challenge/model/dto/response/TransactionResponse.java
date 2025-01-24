package com.enigmacamp.enigshop_challenge.model.dto.response;

import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Customer customer;
    private Date date;
    private List<TransactionDetailResponse> transactionDetails;
    private Long totalPayment;
}
