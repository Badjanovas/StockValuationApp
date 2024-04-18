package com.example.StockValueApp.exception;

public class ValuationDoestExistForSelectedUser extends Exception{
    public ValuationDoestExistForSelectedUser(String message) {
        super(message);
    }
}
