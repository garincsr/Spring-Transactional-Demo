package com.enigmacamp.enigshop_challenge.controller.exception;

import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.utils.customException.InsufficientStockException;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<CommonResponse<String>> handleInsufficientStockException(InsufficientStockException ex){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.GONE.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        CommonResponse<List<Map<String, String>>> response = CommonResponse.<List<Map<String, String>>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed")
                .data(fieldErrors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Integer pageNumber = 0;
        Integer sizeNumber = 10;

        // Check if the parameter name is "page" or "size"
        if ("page".equals(ex.getName())) {
            // Handle page parameter mismatch
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Page parameter is invalid; defaulting to page=0")
                    .data(null) // No data in this case
                    .build();
            return ResponseEntity.ok(response);
        } else if ("size".equals(ex.getName())) {
            // Handle size parameter mismatch
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Size parameter is invalid; defaulting to size=10")
                    .data(null) // No data in this case
                    .build();
            return ResponseEntity.ok(response);
        }

        // For other type mismatches, return a generic error response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request")
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
