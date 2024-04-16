package com.example.StockValueApp.controler;

import com.example.StockValueApp.service.DividendDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dividendDiscount")
@Slf4j
@RequiredArgsConstructor
public class DividendDiscountController {

    private final DividendDiscountService dividendDiscountService;

    @GetMapping("/")
    public ResponseEntity<?> findAllDividendDiscountValuations(){
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getAllDividendDiscountValuations());
    }

}
