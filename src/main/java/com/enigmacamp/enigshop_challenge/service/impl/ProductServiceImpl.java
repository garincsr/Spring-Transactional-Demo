package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.dto.request.ProductRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.ProductResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import com.enigmacamp.enigshop_challenge.repository.ProductRepository;
import com.enigmacamp.enigshop_challenge.repository.specification.ProductSpecification;
import com.enigmacamp.enigshop_challenge.service.ProductService;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<ProductResponse> getAll(SearchRequest request) {
        Sort sort;
        try {
            sort = Sort.by(request.getDirection().equalsIgnoreCase("dsc") ? Sort.Direction.DESC : Sort.Direction.ASC, request.getSort());
        } catch (IllegalArgumentException e) {
            // Jika terjadi IllegalArgumentException, gunakan default
            sort = Sort.by(Sort.Direction.ASC, request.getSort());
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        // Jika query pencarian disediakan, filter hasil
        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            Specification<Product> spec = Specification.where(ProductSpecification.hasName(request.getQuery()));
            return productRepository.findAll(spec,pageable).map(this::mapToResponse);
        }

        return productRepository.findAll(pageable).map(this::mapToResponse);
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
        Product existingProduct = this.findByIdOrThrowNotFound(request.getId());

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

    @Override
    public Product getProductById(String id) {
        return findByIdOrThrowNotFound(id);
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
