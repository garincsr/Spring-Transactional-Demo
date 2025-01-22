package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> addNewProduct(@RequestBody ProductRequest payload){
        ProductResponse product = productService.create(payload);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("New Product Added")
                .data(product)
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct(@RequestParam(name = "search",required = false) String search) {
        List<ProductResponse> productResponses = productService.getAll(search);
        CommonResponse<List<ProductResponse>> commonResponse = CommonResponse.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Product Found")
                .data(productResponses)
                .build();

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id){
      ProductResponse productResponse = productService.getById(id);
      CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
              .status(HttpStatus.OK.value())
              .message("Product Found with ID:" + productResponse.getId())
              .data(productResponse)
              .build();

      return ResponseEntity
              .ok()
              .body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest payload){
        ProductResponse productResponse = productService.updatePut(payload);
        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product Updated with ID" + productResponse.getId())
                .data(productResponse)
                .build();

        return  ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateById(@RequestBody ProductRequest payload){
        ProductResponse productResponse = productService.updatePatch(payload);
        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product Updated with ID" + productResponse.getId())
                .data(productResponse)
                .build();

        return  ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteProduct(@PathVariable String id){
       productService.deleteById(id);
       CommonResponse<String> commonResponse = CommonResponse.<String>builder()
               .status(HttpStatus.OK.value())
               .message("Product Deleted with ID:" + id)
               .build();

       return ResponseEntity
               .ok()
               .body(commonResponse);
    }


}
