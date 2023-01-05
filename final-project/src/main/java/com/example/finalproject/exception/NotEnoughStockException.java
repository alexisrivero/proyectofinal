package com.example.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStockException extends RuntimeException{
    public NotEnoughStockException(String message) {
        super(message);
    }
}
