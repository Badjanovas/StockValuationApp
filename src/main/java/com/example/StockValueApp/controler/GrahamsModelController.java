package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
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

    private final GrahamsModelService service;
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllGrahamsValuations());
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody final GrahamsRequestDTO grahamsRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.saveGrahamsValuation(grahamsRequestDTO));
    }
}
