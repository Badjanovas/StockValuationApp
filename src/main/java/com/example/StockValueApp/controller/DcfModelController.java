package com.example.StockValueApp.controller;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.service.DcfModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/discountedCashFlow")
@Slf4j
@RequiredArgsConstructor
public class DcfModelController {

    private final DcfModelService dcfModelService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() throws NoDcfValuationsFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getAllDcfValuations());
    }

    @GetMapping("/ticker/{ticker}/{userId}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker, @PathVariable final Long userId) throws NoDcfValuationsFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationsByTicker(ticker, userId));
    }

    @GetMapping("/companyName/{companyName}/{userId}")
    public ResponseEntity<?> findByCompanyNAme(@PathVariable final String companyName, @PathVariable final Long userId) throws NoDcfValuationsFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationsByCompanyName(companyName, userId));
    }

    @GetMapping("/date/{date}/{userId}")
    public ResponseEntity<?> findByDate(@PathVariable("date") final LocalDate date, @PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationByDate(date, userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addDcfValuation(@RequestBody final DcfModelRequestDTO dcfModelRequestDTO, @PathVariable final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.addDcfValuation(dcfModelRequestDTO, userId));
    }

    @DeleteMapping("/{dcfValuationId}/{userId}")
    public ResponseEntity<?> deleteDcfValuationById(@PathVariable final Long dcfValuationId, @PathVariable final Long userId) throws NotValidIdException, NoDcfValuationsFoundException, ValuationDoestExistForSelectedUser {
        dcfModelService.deleteDcfValuationById(dcfValuationId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Discounted cash flow valuation with id number " + dcfValuationId + " was deleted from DB successfully.");
    }
}
