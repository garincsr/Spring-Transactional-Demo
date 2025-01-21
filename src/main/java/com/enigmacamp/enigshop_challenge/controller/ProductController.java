package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.Product;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @GetMapping
    public List<Product> getAllProduct(@RequestParam(name = "search",required = false) String search){
        return productService.getAll(search);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
      return productService.getById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product payload){
        return productService.updatePut(payload);
    }

    @PatchMapping("/{id}")
    public Product updateById(@RequestBody Product payload){
        return productService.updatePatch(payload);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id){
       productService.deleteById(id);
       return "Product deleted with ID: " + id;
    }


}
