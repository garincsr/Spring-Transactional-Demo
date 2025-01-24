package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.dto.request.TransactionRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse create (TransactionRequest transactionRequest);

    List<TransactionResponse> getAll();
//    List<TransactionResponse> getAll();
}
