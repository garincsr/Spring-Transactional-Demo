package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.Product;
import com.enigmacamp.enigshop_challenge.repository.ProductRepository;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

//    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        // TODO: Insert Product to Product ID
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll(String search) {
        if (search == null || search.isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.searchProductsByName(search);
    }

    @Override
    public Product getById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public Product updatePut(Product product) {
        Product existingProduct = getById(product.getId());

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return productRepository.saveAndFlush(existingProduct);

    }

    @Override
    public Product updatePatch(Product product) {
        // TODO: Check date avalailable on DB:
        Product existingProduct = getById(product.getId());

        // TODO: Check field want to update:
        if (product.getName() != null) existingProduct.setName(product.getName());
        if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if (product.getPrice() != null) existingProduct.setPrice(product.getPrice());
        if (product.getStock() != null) existingProduct.setStock(product.getStock());

        // TODO: Save product update to DB:
        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = getById(id);
        productRepository.delete(existingProduct);
    }
}
