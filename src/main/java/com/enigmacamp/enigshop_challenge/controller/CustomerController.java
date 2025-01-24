package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.controller.exception.GlobalExceptionController;
import com.enigmacamp.enigshop_challenge.model.dto.request.CustomerRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.CustomerResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.PagingResponse;
import com.enigmacamp.enigshop_challenge.service.CustomerService;
import com.enigmacamp.enigshop_challenge.utils.PagingSizingUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page",defaultValue = "1") String  page,
            @RequestParam(name = "size",defaultValue = "10") String size
    ){
        Integer validatePage = PagingSizingUtils.validatePage(page);
        Integer validateSize = PagingSizingUtils.validateSize(size);

        SearchRequest request = SearchRequest.builder()
                .query(search)
                .page(Math.max(validatePage - 1, 0))
                .size(Math.max(validateSize, 0))
                .build();

        Page<CustomerResponse> customers = customerService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .totalPage(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(validatePage)
                .size(validateSize)
                .hashNext(customers.hasNext())
                .hashPrevious(customers.hasPrevious())
                .build();

        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Customer Found")
                .data(customers.getContent())
                .paging(paging)
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
