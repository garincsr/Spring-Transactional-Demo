package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    Page<ProductResponse> getAll(SearchRequest request);
    ProductResponse getById(String id);
    ProductResponse updatePut(ProductRequest request);
    ProductResponse updatePatch(ProductRequest request);
    void deleteById(String id);
}
