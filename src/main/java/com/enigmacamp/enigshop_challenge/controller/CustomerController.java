package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.CustomerRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody CustomerRequest payload){
        CustomerResponse response = customerService.create(payload);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomer(@RequestParam(name = "search", required = false) String search){
        return customerService.getAll(search);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest payload){
        return customerService.updatePut(payload);
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomerById(@RequestBody CustomerRequest payload){
       return customerService.updatePatch(payload);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id){
       customerService.deleteById(id);
       return "Customer deleted with ID:" + id;
    }
}
