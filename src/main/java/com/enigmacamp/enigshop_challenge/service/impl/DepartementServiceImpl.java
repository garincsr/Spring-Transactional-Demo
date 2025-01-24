package com.enigmacamp.enigshop_challenge.service.impl;

import com.enigmacamp.enigshop_challenge.model.dto.request.DepartementRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.DepartementResponse;
import com.enigmacamp.enigshop_challenge.model.entity.Departement;
import com.enigmacamp.enigshop_challenge.repository.DepartementRepository;
import com.enigmacamp.enigshop_challenge.repository.specification.DepartementSpecification;
import com.enigmacamp.enigshop_challenge.service.DepartementService;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    @Override
    public DepartementResponse create(DepartementRequest request) {
        Departement departement = mapToEntity(request);
        departementRepository.save(departement);
        return mapToResponse(departement);
    }

    @Override
    public Page<DepartementResponse> getAll(SearchRequest request) {
        Sort sort;
        try {
            sort = Sort.by(request.getDirection().equalsIgnoreCase("dsc") ? Sort.Direction.DESC : Sort.Direction.ASC, request.getSort());
        } catch (IllegalArgumentException e) {
            // Jika terjadi IllegalArgumentException, gunakan default
            sort = Sort.by(Sort.Direction.ASC, request.getSort());
        }

        // Buat objek Pageable
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            Specification<Departement> spec = Specification.where(DepartementSpecification.hasName(request.getQuery()));
            return departementRepository.findAll(spec,pageable).map(this::mapToResponse);
        }

        if (request.getCode() != null && !request.getCode().isEmpty()){
           Specification<Departement> spec = Specification.where(DepartementSpecification.hasCode(request.getCode()));
            return departementRepository.findAll(spec,pageable).map(this::mapToResponse);
        }

        return departementRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public DepartementResponse getById(Long id) {
        return this.mapToResponse(this.findByIdOrThrowNotFound(id));
    }

    private Departement findByIdOrThrowNotFound(Long id){
        return departementRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Departement not found", new RuntimeException("Departement not found"))
        );
    }

    @Override
    public DepartementResponse updatePut(DepartementRequest request) {
        this.findByIdOrThrowNotFound(request.getId());
        Departement departement = this.mapToEntity(request);
        return this.mapToResponse(departementRepository.saveAndFlush(departement));
    }

    @Override
    public DepartementResponse updatePatch(DepartementRequest request) {
        Departement existingDepartement = this.findByIdOrThrowNotFound(request.getId());

        if (request.getName() != null) existingDepartement.setName(request.getName());
        if (request.getCode() != null) existingDepartement.setCode(request.getCode());
        if (request.getDescription() != null) existingDepartement.setDescription(request.getDescription());

        return mapToResponse(departementRepository.saveAndFlush(existingDepartement));
    }

    @Override
    public void deleteById(Long id) {
        Departement existingDepartement = this.findByIdOrThrowNotFound(id);
        departementRepository.delete(existingDepartement);
    }

    private Departement mapToEntity(DepartementRequest request){
        return Departement.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
    }

    private DepartementResponse mapToResponse(Departement departement){
        return DepartementResponse.builder()
                .id(departement.getId())
                .name(departement.getName())
                .code(departement.getCode())
                .description(departement.getDescription())
                .build();
    }
}
