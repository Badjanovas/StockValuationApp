package com.example.StockValueApp.service;

import com.example.StockValueApp.model.DividendDiscountModel;
import com.example.StockValueApp.repository.DividendDiscountRepository;
import com.example.StockValueApp.service.mappingService.DividendDiscountMappingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Data
public class DividendDiscountService {

    private final DividendDiscountRepository dividendDiscountRepository;
    private final DividendDiscountMappingService dividendDiscountMappingService;

    public List<DividendDiscountModel> getAllDividendDiscountValuations(){
        final List<DividendDiscountModel> dividendDiscountValuations = dividendDiscountRepository.findAll();

        return dividendDiscountValuations;
    }

}
