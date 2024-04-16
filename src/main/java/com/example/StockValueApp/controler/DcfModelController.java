package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
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

    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker) throws NoDcfValuationsFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationsByTicker(ticker));
    }

    @GetMapping("/companyName/{companyName}")
    public ResponseEntity<?> findByCompanyNAme(@PathVariable final String companyName) throws NoDcfValuationsFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationsByCompanyName(companyName));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable("date") final LocalDate date) throws NoGrahamsModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.getDcfValuationByDate(date));
    }

    @PostMapping("/")
    public ResponseEntity<?> addDcfValuation(@RequestBody final DcfModelRequestDTO dcfModelRequestDTO) throws MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.addDcfValuation(dcfModelRequestDTO));
    }

    @DeleteMapping("/{dcfValuationId}")
    public ResponseEntity<?> deleteDcfValuationById(@PathVariable final Long dcfValuationId) throws NotValidIdException, NoDcfValuationsFoundException {
        dcfModelService.deleteDcfValuationById(dcfValuationId);
        return ResponseEntity.status(HttpStatus.OK).body("Discounted cash flow valuation with id number " + dcfValuationId + " was deleted from DB successfully.");
    }
}
