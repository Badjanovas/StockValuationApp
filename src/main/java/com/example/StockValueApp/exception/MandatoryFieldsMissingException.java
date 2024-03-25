package com.example.StockValueApp.exception;

public class MandatoryFieldsMissingException extends Exception{
    public MandatoryFieldsMissingException(String message){
        super(message);
    }
}
