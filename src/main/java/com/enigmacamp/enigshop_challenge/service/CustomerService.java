package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    List<Customer> getAll(String name);
    Customer getById(String id);
    Customer updatePut(Customer customer);
    Customer updatePatch(Customer customer);
    void deleteById(String id);
}
