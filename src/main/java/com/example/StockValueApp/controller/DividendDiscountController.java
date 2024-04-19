package com.example.StockValueApp.controller;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.service.DividendDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dividendDiscount")
@Slf4j
@RequiredArgsConstructor
public class DividendDiscountController {

    private final DividendDiscountService dividendDiscountService;

    @GetMapping("/")
    public ResponseEntity<?> findAllDividendDiscountValuations() throws NoDividendDiscountModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getAllDividendDiscountValuations());
    }

    @GetMapping("/ticker/{ticker}/{userId}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker, @PathVariable final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByTicker(ticker, userId));
    }

    @GetMapping("/companyName/{companyName}/{userId}")
    public ResponseEntity<?> findByCompanyName(@PathVariable final String companyName, @PathVariable final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByCompanyName(companyName, userId));
    }

    @GetMapping("/date/{date}/{userId}")
    public ResponseEntity<?> findByDate(@PathVariable("date") final LocalDate date, final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByDate(date,userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addDividendDiscountValuation(@RequestBody final DividendDiscountRequestDTO dividendDiscountRequestDTO, @PathVariable final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException, IncorrectCompaniesExpectedGrowthException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.addDividendDiscountValuation(dividendDiscountRequestDTO, userId));
    }

    @DeleteMapping("/{valuationId}/{userId}")
    public ResponseEntity<?> deleteDividendDiscountById(@PathVariable final  Long valuationId, @PathVariable final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, ValuationDoestExistForSelectedUser {
        dividendDiscountService.deleteDividendDiscountValuationById(valuationId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Dividend discount valuation with id number " + valuationId + " was deleted from DB successfully.");
    }

}
