package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import com.enigmacamp.enigshop_challenge.service.ProductService;
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
    public ProductResponse addNewProduct(@RequestBody ProductRequest payload){
        return productService.create(payload);
    }

    @GetMapping
    public List<ProductResponse> getAllProduct(@RequestParam(name = "search",required = false) String search){
        return productService.getAll(search);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id){
      return productService.getById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@RequestBody ProductRequest payload){
        return productService.updatePut(payload);
    }

    @PatchMapping("/{id}")
    public ProductResponse updateById(@RequestBody ProductRequest payload){
        return productService.updatePatch(payload);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id){
       productService.deleteById(id);
       return "Product deleted with ID: " + id;
    }


}
