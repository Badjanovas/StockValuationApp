package com.example.StockValueApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GrahamsRequestDTO {

    private String companyName;
    private String CompanyTicker;
    private Double eps;
    private Double growthRate;
    private Double currentYieldOfBonds;

}
