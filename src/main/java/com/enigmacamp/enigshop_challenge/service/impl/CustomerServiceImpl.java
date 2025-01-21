package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.Customer;
import com.enigmacamp.enigshop_challenge.repository.CustomerRepository;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll(String search) {
        System.out.println("INI SEARCH:" + search);

        if (search == null || search.isEmpty()) {
            return customerRepository.findAll();
        }
        return customerRepository.findByFullNameContainingIgnoreCase(search);
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new RuntimeException("Customer not found!"));

    }

    @Override
    public Customer updatePut(Customer customer) {
        Customer existingCustomer = getById(customer.getId());

        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setEmail(customer.getFullName());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setIsActive(customer.getIsActive());

        return customerRepository.saveAndFlush(existingCustomer);
    }

    @Override
    public Customer updatePatch(Customer customer) {
        Customer existingCustomer = getById(customer.getId());

        if (customer.getFullName() != null) existingCustomer.setFullName(customer.getFullName());
        if (customer.getEmail() != null) existingCustomer.setEmail(customer.getEmail());
        if (customer.getPhoneNumber() != null) existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getIsActive() != null) existingCustomer.setIsActive(customer.getIsActive());

        return customerRepository.saveAndFlush(existingCustomer);

    }

    @Override
    public void deleteById(String id) {
        Customer existingCustomer = getById(id);
        customerRepository.delete(existingCustomer);
    }
}
