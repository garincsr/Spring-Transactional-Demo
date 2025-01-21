package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.Customer;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addNewCustomer(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomer(@RequestParam(name = "search", required = false) String search){
        return customerService.getAll(search);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@RequestBody Customer payload){
        return customerService.updatePut(payload);
    }

    @PatchMapping("/{id}")
    public Customer updateCustomerById(@RequestBody Customer payload){
       return customerService.updatePatch(payload);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id){
       customerService.deleteById(id);
       return "Customer deleted with ID:" + id;
    }
}
