package com.example.StockValueApp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrahamsResponseDTO {

    private String companyName;
    private String companyTicker;
    //Earnings per share
    private Double eps;
    private Double growthRate;
    private Double currentYieldOfBonds;
    private Double intrinsicValue;
    private LocalDateTime creationDate;

}
