package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.dto.request.CustomerRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import com.enigmacamp.enigshop_challenge.repository.CustomerRepository;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        Customer newCustomer = this.mapToEntity(request);
        newCustomer = customerRepository.save(newCustomer);
        return this.mapToResponse(newCustomer);
    }

    @Override
    public Page<CustomerResponse> getAll(SearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return customerRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public CustomerResponse getById(String id) {
        return mapToResponse(this.findByIdOrThrowNotFound(id));
    }

    private Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer Not Found", new RuntimeException("Customer Not Found"))
        );
    }

    @Override
    public CustomerResponse updatePut(CustomerRequest request) {
        this.findByIdOrThrowNotFound(request.getId());
        Customer customer = this.mapToEntity(request);
        return this.mapToResponse(customerRepository.saveAndFlush(customer));
    }

    @Override
    public CustomerResponse updatePatch(CustomerRequest request) {
        Customer existingCustomer = this.findByIdOrThrowNotFound(request.getId());

        if (request.getFullName() != null) existingCustomer.setFullName(request.getFullName());
        if (request.getEmail() != null) existingCustomer.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) existingCustomer.setPhoneNumber(request.getPhoneNumber());
        if (request.getAddress() != null) existingCustomer.setAddress(request.getAddress());
        if (request.getIsActive() != null) existingCustomer.setIsActive(request.getIsActive());

        return  mapToResponse(customerRepository.saveAndFlush(existingCustomer));
    }

    @Override
    public void deleteById(String id) {
        Customer existingCustomer = this.findByIdOrThrowNotFound(id);
        customerRepository.delete(existingCustomer);
    }

    private Customer mapToEntity(CustomerRequest request){
        return Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .isActive(request.getIsActive())
                .build();
    }

    private CustomerResponse mapToResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .isActive(customer.getIsActive())
                .build();
    }
}
