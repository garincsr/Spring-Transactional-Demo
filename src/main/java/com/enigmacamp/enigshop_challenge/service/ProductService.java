package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> getAll(String search);
    Product getById(String id);
    Product updatePut(Product product);
    Product updatePatch(Product product);
    void deleteById(String id);
}
