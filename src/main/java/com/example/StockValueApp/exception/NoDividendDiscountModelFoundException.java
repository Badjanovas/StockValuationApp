package com.example.StockValueApp.exception;

import org.springframework.data.jpa.repository.JpaRepository;

public class NoDividendDiscountModelFoundException extends Exception {

    public NoDividendDiscountModelFoundException(String message) {
        super(message);
    }
}
