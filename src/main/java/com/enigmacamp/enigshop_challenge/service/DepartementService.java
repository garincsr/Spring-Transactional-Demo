package com.enigmacamp.enigshop_challenge.service;

import com.enigmacamp.enigshop_challenge.model.dto.request.DepartementRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.DepartementResponse;
import org.springframework.data.domain.Page;

public interface DepartementService {
    DepartementResponse create(DepartementRequest request);
    Page<DepartementResponse> getAll(SearchRequest request);
    DepartementResponse getById(Long id);
    DepartementResponse updatePut(DepartementRequest request);
    DepartementResponse updatePatch(DepartementRequest request);
    void deleteById(Long id);
}
