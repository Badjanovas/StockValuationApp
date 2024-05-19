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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/discountedCashFlow")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200",
        "https://stock-value-app.s3.eu-west-2.amazonaws.com/",
        "https://main.d3gj5nyzm6hb6g.amplifyapp.com/"
})
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

    @GetMapping("/date/{startDate}/{endDate}/{userId}")
    public ResponseEntity<?> findByDate(@PathVariable("startDate") final LocalDate startDate, @PathVariable("endDate") final LocalDate endDate, @PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationByDate(startDate, endDate, userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addDcfValuation(@RequestBody final DcfModelRequestDTO dcfModelRequestDTO, @PathVariable final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.addDcfValuation(dcfModelRequestDTO, userId));
    }

    @DeleteMapping("/{dcfValuationId}/{userId}")
    public ResponseEntity<?> deleteDcfValuationById(@PathVariable final Long dcfValuationId, @PathVariable final Long userId) throws NotValidIdException, NoDcfValuationsFoundException, ValuationDoestExistForSelectedUserException {
        dcfModelService.deleteDcfValuationById(dcfValuationId, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Discounted cash flow valuation with id number " + dcfValuationId + " was deleted from DB successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
