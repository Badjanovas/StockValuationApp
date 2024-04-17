package com.example.StockValueApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DividendDiscountResponseDTO implements Serializable {

    private String companyName;
    private String ticker;
    private Double currentYearsDiv;
    private Double valueOfNextYearsDiv;
    private Double wacc;
    private Double expectedGrowthRate;
    private Double intrinsicValue;
    private LocalDate creationDate;
}
