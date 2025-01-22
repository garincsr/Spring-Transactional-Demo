package com.enigmacamp.enigshop_challenge.controller.exception;

import com.enigmacamp.enigshop_challenge.model.dto.response.CommonResponse;
import com.enigmacamp.enigshop_challenge.utils.customException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
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
}
