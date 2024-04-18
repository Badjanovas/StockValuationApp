package com.example.StockValueApp.handler;

import com.example.StockValueApp.exception.IncorrectCompaniesExpectedGrowthException;
import com.example.StockValueApp.exception.NoDividendDiscountModelFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DividendDiscountExceptionHandler {

    @ExceptionHandler(NoDividendDiscountModelFoundException.class)
    public ResponseEntity<Object> handleNoDividendDiscountModelFoundException(NoDividendDiscountModelFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectCompaniesExpectedGrowthException.class)
    public ResponseEntity<Object> handleIncorrectCompaniesExpectedGrowthException(IncorrectCompaniesExpectedGrowthException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
