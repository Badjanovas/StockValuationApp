package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.service.DcfModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<?> addDcfValuation(@RequestBody final DcfModelRequestDTO dcfModelRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(dcfModelService.addDcfValuation(dcfModelRequestDTO));
    }
}
