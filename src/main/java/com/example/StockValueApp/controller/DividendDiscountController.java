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
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/date/{startDate}/{endDate}/{userId}")
    public ResponseEntity<?> findByDate(@PathVariable("startDate") final LocalDate startDate, @PathVariable final LocalDate endDate, @PathVariable final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByDate(startDate, endDate, userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addDividendDiscountValuation(@RequestBody final DividendDiscountRequestDTO dividendDiscountRequestDTO, @PathVariable final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException, IncorrectCompaniesExpectedGrowthException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.addDividendDiscountValuation(dividendDiscountRequestDTO, userId));
    }

    @DeleteMapping("/{valuationId}/{userId}")
    public ResponseEntity<?> deleteDividendDiscountById(@PathVariable final  Long valuationId, @PathVariable final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, ValuationDoestExistForSelectedUserException {
        dividendDiscountService.deleteDividendDiscountValuationById(valuationId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Dividend discount valuation with id number " + valuationId + " was deleted from DB successfully.");
    }

}
