package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private List<Customer> customerList = new ArrayList<>();

    public CustomerController() {
        customerList.add(new Customer("Maggie Lindemann","maggie12@gmail.com","+12351725798","California, ST",true));
        customerList.add(new Customer("Lebron James","james23@gmail.com","+112877882233","Washington, DC",true));
    }

    @PostMapping
    public Customer addNewCustomer(@RequestBody Customer customer){
        Customer newCustomer = new Customer(customer.getFullName(), customer.getEmail(), customer.getPhoneNumber(), customer.getAddress(), customer.getActive());
        customerList.add(newCustomer);
        return newCustomer;
    }

    @GetMapping
    public List<Customer> getAllCustomer(@RequestParam(name = "search",required = false) String search){
        if (search != null && !search.trim().isEmpty()){
            return customerList.stream()
                    .filter(customer -> customer.getFullName().toLowerCase().contains(search))
                    .toList();
        } else {
            throw new RuntimeException("No customer found");
        }
    }

    @GetMapping("/{productName}")
    public Customer getCustomerByName(@PathVariable String customerName){
        return customerList.stream()
                .filter(c -> Objects.equals(c.getFullName(), customerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found with name: " + customerName));
    }

    @PatchMapping
    public Customer updateCustomerByIndex(@RequestBody int index, Customer updatedCustomer){
        if (index >= 0 && index < customerList.size()){
            Customer existingCustomer = customerList.get(index);
            existingCustomer.setFullName(updatedCustomer.getFullName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setActive(updatedCustomer.getActive());
            return existingCustomer;
        } else {
            throw new RuntimeException("Update customer failed");
        }
    }

    @DeleteMapping("/{index}")
    public String deleteCustomer(@PathVariable int index){
        customerList.remove(index);
        return "Customer successfully deleted";
    }
}
