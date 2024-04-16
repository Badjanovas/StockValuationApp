package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDividendDiscountModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
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

    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker) throws NoDividendDiscountModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByTicker(ticker));
    }

    @GetMapping("/companyName/{companyName}")
    public ResponseEntity<?> findByCompanyName(@PathVariable final String companyName) throws NoDividendDiscountModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByCompanyName(companyName));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable("date") final LocalDate date) throws NoDividendDiscountModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.getDividendDiscountValuationsByDate(date));
    }

    @PostMapping("/")
    public ResponseEntity<?> addDividendDiscountValuation(@RequestBody final DividendDiscountRequestDTO dividendDiscountRequestDTO) throws MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(dividendDiscountService.addDividendDiscountValuation(dividendDiscountRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDividendDiscountById(@PathVariable final  Long id) throws NoDividendDiscountModelFoundException, NotValidIdException {
        dividendDiscountService.deleteDividendDiscountValuationById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Dividend discount valuation with id number " + id + " was deleted from DB successfully.");
    }

}
