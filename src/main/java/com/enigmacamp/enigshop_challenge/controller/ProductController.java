package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final List<Product> productList = new ArrayList<>();

    public ProductController(){
        productList.add(new Product("Alienware X15", 28000000.0, 10));
        productList.add(new Product("Legion Pro", 23500000.0, 25));
        productList.add(new Product("Blade 15", 33800000.0, 5));
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        Product newProduct = new Product(product.getName(), product.getPrice(), product.getStock());
        productList.add(newProduct);

        return newProduct;
    }

    @GetMapping
    public List<Product> getAllProduct(@RequestParam(name = "search",required = false) String search){
        if (search != null && !search.trim().isEmpty()) {
            return productList.stream()
                    .filter(product -> product.getName().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }
        return productList;
    }

    @GetMapping("/{productName}")
    public Product getProductByName(@PathVariable String productName){
       return productList.stream()
               .filter(product -> Objects.equals(product.getName(), productName))
               .findFirst()
               .orElseThrow(() -> new RuntimeException("Product not found with name: " + productName));
    }

//    @PatchMapping
//    public Product updateById(@RequestBody UUID productId, Product updatedProduct){
//        return productList.stream()
//                .filter(product -> product.getId() == productId)
//                .findFirst()
//                .map(product -> {
//                    product.setName(updatedProduct.getName());
//                    product.setPrice(updatedProduct.getPrice());
//                    product.setStock(updatedProduct.getStock());
//                    return product;
//                })
//                .orElseThrow(() -> new RuntimeException("Product update failed with id: " + productId));
//    }

    @PatchMapping
    public Product updateByIndex(int index, Product updatedProduct){
        if (index >= 0 && index < productList.size()){
            Product existingProduct = productList.get(index);
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            return existingProduct;
        } else {
            throw new RuntimeException("Update product failed");
        }

    }

    @DeleteMapping("/{index}")
    public String deleteProduct(@PathVariable int index){
        productList.remove(index);
        return "Product deleted with index: " + index;
    }


}
