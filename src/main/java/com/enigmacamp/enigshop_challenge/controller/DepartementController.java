package com.enigmacamp.enigshop_challenge.controller;

import com.enigmacamp.enigshop_challenge.constant.APIUrl;
import com.enigmacamp.enigshop_challenge.model.dto.request.DepartementRequest;
import com.enigmacamp.enigshop_challenge.model.dto.request.SearchRequest;
import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.DepartementResponse;
import com.enigmacamp.enigshop_challenge.model.dto.response.PagingResponse;
import com.enigmacamp.enigshop_challenge.service.DepartementService;
import com.enigmacamp.enigshop_challenge.utils.PagingSizingUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.DEPARTEMENT_API)
public class DepartementController {

    private final DepartementService departementService;

    @PostMapping
    public ResponseEntity<CommonResponse<DepartementResponse>> addNewDepartemnt(@Valid @RequestBody DepartementRequest payload){
        DepartementResponse departement = departementService.create(payload);
        CommonResponse<DepartementResponse> response = CommonResponse.<DepartementResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("New Departement Added")
                .data(departement)
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<DepartementResponse>>> getAllDepartement(
            @RequestParam(name = "search",required = false) String name,
            @RequestParam(name = "code",required = false) String code,
            @RequestParam(name = "page",defaultValue = "1") String page,
            @RequestParam(name = "size",defaultValue = "10") String size,
            @RequestParam(name = "sort",defaultValue = "name") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {

        Integer validatedPage = PagingSizingUtils.validatePage(page);
        Integer validatedSize = PagingSizingUtils.validateSize(size);

        SearchRequest request = SearchRequest.builder()
                .query(name)
                .code(code)
                .page(Math.max(validatedPage - 1 ,0))
                .size(Math.max(validatedSize, 0))
                .sort(sort)
                .direction(direction)
                .build();

        Page<DepartementResponse> departements = departementService.getAll(request);

        PagingResponse paging = PagingResponse.builder()
                .totalPage(departements.getTotalPages())
                .totalElement(departements.getTotalElements())
                .page(validatedPage)
                .size(validatedSize)
                .hashNext(departements.hasNext())
                .hashPrevious(departements.hasPrevious())
                .build();

        CommonResponse<List<DepartementResponse>> commonResponse = CommonResponse.<List<DepartementResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Product Found")
                .data(departements.getContent())
                .paging(paging)
                .build();

        return ResponseEntity
                .ok()
                .header("Content type", "Apllication/json")
                .body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<DepartementResponse>> getDepartementById(@PathVariable Long id){
        DepartementResponse departement = departementService.getById(id);
        CommonResponse<DepartementResponse> responses = CommonResponse.<DepartementResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product Found with ID:" + departement.getId())
                .data(departement)
                .build();

        return ResponseEntity
                .ok()
                .body(responses);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<DepartementResponse>> updatePutDepartement(@RequestBody DepartementRequest payload){
        DepartementResponse departement = departementService.updatePut(payload);
        CommonResponse<DepartementResponse> response = CommonResponse.<DepartementResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product Updated with ID" + departement.getId())
                .data(departement)
                .build();

        return  ResponseEntity
                .ok()
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<DepartementResponse>> updatePatchDepartement(@RequestBody DepartementRequest payload){
        DepartementResponse departement = departementService.updatePatch(payload);
        CommonResponse<DepartementResponse> response = CommonResponse.<DepartementResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product Updated with ID" + departement.getId())
                .data(departement)
                .build();

        return  ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteDepartement(@PathVariable Long id){
        departementService.deleteById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Product Deleted with ID:" + id)
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
