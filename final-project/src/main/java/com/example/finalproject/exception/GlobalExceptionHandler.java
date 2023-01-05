package com.example.finalproject.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception)
    {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {RequiredInformationNullException.class})
    public ResponseEntity<Object> handleRequiredInformationNullException(RequiredInformationNullException exception)
    {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotEnoughStockException.class})
    public ResponseEntity<Object> handleNotEnoughStockException(NotEnoughStockException exception)
    {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotEnoughFoundsException.class})
    public ResponseEntity<Object> handleNotEnoughFoundsException(NotEnoughFoundsException exception)
    {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceAlreadyExistException.class})
    public ResponseEntity<Object> handleResourceAlreadyExistException(ResourceAlreadyExistException exception)
    {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }
}
