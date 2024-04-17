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
public class GrahamsResponseDTO implements Serializable {

    private String companyName;
    private String companyTicker;
    //Earnings per share
    private Double eps;
    private Double growthRate;
    private Double currentYieldOfBonds;
    private Double intrinsicValue;
    private LocalDate creationDate;

}
