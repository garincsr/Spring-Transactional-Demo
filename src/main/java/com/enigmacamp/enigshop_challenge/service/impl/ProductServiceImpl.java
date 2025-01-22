package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import com.enigmacamp.enigshop_challenge.repository.ProductRepository;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

//    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = this.mapToEntity(request);
        product = productRepository.save(product);
        return this.mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAll(String name) {
       if (name != null && !name.isEmpty()){
           return productRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToResponse).toList();
       }
       return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public ProductResponse getById(String id) {
        return this.mapToResponse(this.findByIdOrThrowNotFound(id));
    }

    private Product findByIdOrThrowNotFound(String id){
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found", new RuntimeException("Product not found"))
        );
    }

    @Override
    public ProductResponse updatePut(ProductRequest request) {
        this.findByIdOrThrowNotFound(request.getId());
        Product product = this.mapToEntity(request);
        return this.mapToResponse(productRepository.saveAndFlush(product));
    }

    @Override
    public ProductResponse updatePatch(ProductRequest request) {
        // TODO: Check date avalailable on DB:
        Product existingProduct = findByIdOrThrowNotFound(request.getId());

        // TODO: Check field want to update:
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getDescription() != null) existingProduct.setDescription(request.getDescription());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());
        if (request.getStock() != null) existingProduct.setStock(request.getStock());

        // TODO: Save product update to DB:
        return mapToResponse(productRepository.saveAndFlush(existingProduct));
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = this.findByIdOrThrowNotFound(id);
        productRepository.delete(existingProduct);
    }

    private ProductResponse mapToResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    private Product mapToEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }
}
