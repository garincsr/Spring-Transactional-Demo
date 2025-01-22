package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.CustomerRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommonResponse<CustomerResponse>> addNewCustomer(@Valid @RequestBody CustomerRequest payload){
        CustomerResponse customerResponse = customerService.create(payload);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("New Customer Added")
                .data(customerResponse)
                .build();
        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(@RequestParam(name = "search", required = false) String search){
        List<CustomerResponse> customerResponses = customerService.getAll(search);
        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Customer Found")
                .data(customerResponses)
                .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id){
        CustomerResponse customerResponse = customerService.getById(id);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Customer Found with ID: " + customerResponse.getId())
                .data(customerResponse)
                .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerRequest payload){
        CustomerResponse customerResponse = customerService.updatePut(payload);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Customer Updated with ID: " + customerResponse.getId())
                .data(customerResponse)
                .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomerById(@RequestBody CustomerRequest payload){
       CustomerResponse customerResponse = customerService.updatePatch(payload);
       CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
               .status(HttpStatus.OK.value())
               .message("Customer Updated with ID: " + customerResponse.getId())
               .data(customerResponse)
               .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id){
       customerService.deleteById(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Customer Deleted with ID:" + id)
                .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }
}
