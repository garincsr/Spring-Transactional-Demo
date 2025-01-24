package com.enigmacamp.enigshop_challenge.utils.customException;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
