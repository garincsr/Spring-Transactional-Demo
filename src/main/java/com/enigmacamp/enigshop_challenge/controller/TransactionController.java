package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.TransactionRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.TransactionResponse;
import com.enigmacamp.enigshop_challenge.service.TransactionService;
import com.enigmacamp.enigshop_challenge.utils.customException.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = APIUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> addNewProduct(@RequestBody TransactionRequest payload){
      TransactionResponse transaction = transactionService.create(payload);

      CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
              .status(HttpStatus.CREATED.value())
              .message("New Product Added")
              .data(transaction)
              .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
