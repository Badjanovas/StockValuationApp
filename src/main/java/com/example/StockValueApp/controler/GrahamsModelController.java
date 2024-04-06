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

@RestController
@RequestMapping("/api/grahams")
@Slf4j
@RequiredArgsConstructor
public class GrahamsModelController {

    private final GrahamsModelService grahamsModelService;
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.getAllGrahamsValuations());
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody final GrahamsRequestDTO grahamsRequestDTO) throws MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.addGrahamsValuation(grahamsRequestDTO));
    }

    @DeleteMapping("/{grahamsValuationId}")
    public ResponseEntity<?> deleteGrahamsModel(@PathVariable final Long grahamsValuationId) throws NoGrahamsModelFoundException, NotValidIdException {
        return ResponseEntity.status(HttpStatus.OK).body(grahamsModelService.deleteGrahamsModelById(grahamsValuationId));
    }
}
