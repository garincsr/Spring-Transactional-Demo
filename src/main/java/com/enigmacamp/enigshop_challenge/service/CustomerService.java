package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.dto.request.CustomerRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    List<CustomerResponse> getAll(String name);
    CustomerResponse getById(String id);
    CustomerResponse updatePut(CustomerRequest request);
    CustomerResponse updatePatch(CustomerRequest request);
    void deleteById(String id);
}
