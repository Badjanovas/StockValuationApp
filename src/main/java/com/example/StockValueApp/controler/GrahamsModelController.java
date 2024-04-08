package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
import com.example.StockValueApp.service.GrahamsModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/grahams")
@Slf4j
@RequiredArgsConstructor
public class GrahamsModelController {

    private final GrahamsModelService grahamsModelService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() throws NoGrahamsModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getAllGrahamsValuations());
    }

    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker) throws NoGrahamsModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByTicker(ticker));
    }

    @GetMapping("/companyName/{companyName}")
    public ResponseEntity<?> findByCompanyName(@PathVariable final String companyName) throws NoGrahamsModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByCompanyName(companyName));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByCreationDate(@PathVariable("date") final LocalDate date) throws NoGrahamsModelFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByDate(date));
    }

    @PostMapping("/")
    public ResponseEntity<?> addGrahamsValuation(@RequestBody final GrahamsRequestDTO grahamsRequestDTO) throws MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.addGrahamsValuation(grahamsRequestDTO));
    }

    @DeleteMapping("/{grahamsValuationId}")
    public ResponseEntity<?> deleteGrahamsModel(@PathVariable final Long grahamsValuationId) throws NoGrahamsModelFoundException, NotValidIdException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.deleteGrahamsValuationById(grahamsValuationId));
    }
}
