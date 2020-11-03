package com.example.vanilla.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//adnotacja pozwala na obsługę wyjątków w calej aplikacji, nie tylko dla pojedyńczego kontrolera
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exception (NotFoundException exception) {
        return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> exception (ConflictException exception) {
        return new ResponseEntity<>("Movie is already in query", HttpStatus.CONFLICT);
    }


}
