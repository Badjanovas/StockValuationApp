package com.example.StockValueApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DividendDiscountRequestDTO  {

    private String companyName;
    private String ticker;
    private Double currentYearsDiv;
    // turi buti procentais
    private Double wacc;
    // turi buti procentais
    private Double expectedGrowthRate;

}
