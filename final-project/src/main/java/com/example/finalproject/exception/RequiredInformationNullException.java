package com.example.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequiredInformationNullException extends RuntimeException{
    public RequiredInformationNullException(String message) {
        super(message);
    }
}
