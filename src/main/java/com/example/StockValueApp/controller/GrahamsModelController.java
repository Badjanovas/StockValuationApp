package com.example.StockValueApp.controller;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.exception.*;
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

    @GetMapping("/ticker/{ticker}/{userId}")
    public ResponseEntity<?> findByTicker(@PathVariable final String ticker, @PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByTicker(ticker, userId));
    }

    @GetMapping("/companyName/{companyName}/{userId}")
    public ResponseEntity<?> findByCompanyName(@PathVariable final String companyName, @PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByCompanyName(companyName, userId));
    }

    @GetMapping("/date/{startDate}/{endDate}/{userId}")
    public ResponseEntity<?> findByCreationDate(@PathVariable("startDate") final LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getGrahamsValuationsByDate(startDate, endDate, userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addGrahamsValuation(@RequestBody final GrahamsRequestDTO grahamsRequestDTO, @PathVariable final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.addGrahamsValuation(grahamsRequestDTO, userId));
    }

    @DeleteMapping("/{grahamsValuationId}/{userId}")
    public ResponseEntity<?> deleteGrahamsModel(@PathVariable final Long grahamsValuationId,@PathVariable final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, ValuationDoestExistForSelectedUserException, NoUsersFoundException {
        grahamsModelService.deleteGrahamsValuationById(grahamsValuationId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Grahams valuation  with id number " + grahamsValuationId + " was deleted from DB successfully.");
    }
}