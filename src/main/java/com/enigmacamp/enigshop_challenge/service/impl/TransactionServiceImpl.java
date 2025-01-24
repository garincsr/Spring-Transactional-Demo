package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.TransactionRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.TransactionDetailResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.TransactionResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import com.enigmacamp.enigshop_challenge.model.entity.Transaction;
import com.enigmacamp.enigshop_challenge.model.entity.TransactionDetail;
import com.enigmacamp.enigshop_challenge.repository.TransactionDetailRepository;
import com.enigmacamp.enigshop_challenge.repository.TransactionRepository;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import com.enigmacamp.enigshop_challenge.service.TransactionService;
import com.enigmacamp.enigshop_challenge.utils.customException.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest){
        Customer customer = customerService.getCustomerById(transactionRequest.getCustomerId());
        Date currentDate = new Date();

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(currentDate)
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails().stream().map(detailRequest -> {
            Product product = productService.getProductById(detailRequest.getProductId());
            //TODO: Make sure quantity request less or equal than stock product
            if (detailRequest.getQty() > product.getStock()){
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName(),
                        new RuntimeException("Insufficient stock for product"));
            }
            //TODO: Update stock Product Entity
            product.setStock(product.getStock() - detailRequest.getQty());
            ProductRequest stockChanged = ProductRequest.builder()
                    .id(product.getId())
                    .stock(product.getStock())
                    .build();
            productService.updatePatch(stockChanged);

            TransactionDetail trxDetail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .qty(detailRequest.getQty())
                    .productPrice(product.getPrice())
                    .build();

            totalPayment.updateAndGet(v -> v + product.getPrice()*detailRequest.getQty());

            transactionDetailRepository.save(trxDetail);
            return trxDetail;
        }).toList();

        transaction.setTransactionDetails(transactionDetails);

        Transaction resultTransaction = transactionRepository.save(transaction);

        List<TransactionDetailResponse> transactionDetailResponses = resultTransaction.getTransactionDetails().stream().map(trxdetail -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(trxdetail.getProduct().getId())
                    .name(trxdetail.getProduct().getName())
                    .description(trxdetail.getProduct().getDescription())
                    .price(trxdetail.getProductPrice())
                    .stock(trxdetail.getProduct().getStock())
                    .build();

           return TransactionDetailResponse.builder()
                   .id(trxdetail.getId())
                   .productResponse(productResponse)
                   .productPrice(trxdetail.getProductPrice())
                   .qty(trxdetail.getQty())
                   .build();
        }).toList(); // --> List<TransactionDetailResponse>

        return TransactionResponse.builder()
                .id(resultTransaction.getId())
                .customer(resultTransaction.getCustomer())
                .date(resultTransaction.getTransactionDate())
                .transactionDetails(transactionDetailResponses)// Need more mapping
                .totalPayment(totalPayment.get())
                .build();
    }

    @Override
    public List<TransactionResponse> getAll(){
        List<Transaction> transactions = transactionRepository.findAll();

        List<TransactionResponse> responses = transactions.stream().map(transaction -> {
            List<TransactionDetailResponse> detailResponses = transaction.getTransactionDetails().stream().map(trxDetail -> {
                ProductResponse productResponse = ProductResponse.builder()
                        .id(trxDetail.getProduct().getId())
                        .name(trxDetail.getProduct().getName())
                        .description(trxDetail.getProduct().getDescription())
                        .price(trxDetail.getProductPrice())
                        .stock(trxDetail.getProduct().getStock())
                        .build();

                return TransactionDetailResponse.builder()
                        .id(trxDetail.getId())
                        .productResponse(productResponse)
                        .productPrice(trxDetail.getProductPrice())
                        .qty(trxDetail.getQty())
                        .build();
            }).toList();

           return TransactionResponse.builder()
                   .id(transaction.getId())
                   .customer(transaction.getCustomer())
                   .date(transaction.getTransactionDate())
                   .transactionDetails(detailResponses)
                   .build();
        }).toList();

        return responses;
    }

}
