package com.enigmacamp.enigshop_challenge.repository;

import com.enigmacamp.enigshop_challenge.model.entity.Departement;
import com.enigmacamp.enigshop_challenge.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Long>, JpaSpecificationExecutor<Departement> {
    Page<Departement> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
