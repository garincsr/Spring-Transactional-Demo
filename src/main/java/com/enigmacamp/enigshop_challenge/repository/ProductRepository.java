package com.enigmacamp.enigshop_challenge.repository;

import com.enigmacamp.enigshop_challenge.model.entity.Customer;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
//    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))")
//    List<Product> searchProductsByName(String search);
}
