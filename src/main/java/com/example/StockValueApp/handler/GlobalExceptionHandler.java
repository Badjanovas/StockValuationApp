package com.example.StockValueApp.handler;

import com.example.StockValueApp.exception.NotValidIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotValidIdException.class)
    public ResponseEntity<Object> handleNotValidIdException(NotValidIdException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception e) {
//        log.error("An unexpected error has occurred: " + e.getMessage());
//        return new ResponseEntity<>("An unexpected error has occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
