package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.controller.exception.GlobalExceptionController;
import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.PagingResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct(
            @RequestParam(name = "search",required = false) String name,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "10") Integer size,
            @RequestParam(name = "sort",defaultValue = "id", required = false) String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {

        if (isDigit(page.toString()) || page <= 0) {
            page = 1;
        }

        if (isDigit(size.toString()) || size <= 0) {
            size = 10;
        }


        SearchRequest request = SearchRequest.builder()
                .query(name)
                .page(Math.max(page - 1, 0))
                .size(Math.max(size, 0))
                .sort(sort)
                .direction(direction)
                .build();

        Page<ProductResponse> products = productService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .totalPage(products.getTotalPages())
                .totalElement(products.getTotalElements())
                .page(page - 1)
                .size(size)
                .hashNext(products.hasNext())
                .hashPrevious(products.hasPrevious())
                .build();

        CommonResponse<List<ProductResponse>> commonResponse = CommonResponse.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Product Found")
                .data(products.getContent())
                .paging(paging)
                .build();

        return ResponseEntity
                .ok()
                .header("Content type", "Apllication/json")
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

    private boolean isDigit(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
}
