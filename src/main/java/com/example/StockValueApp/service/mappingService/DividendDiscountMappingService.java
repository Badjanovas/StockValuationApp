package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.dto.DividendDiscountResponseDTO;
import com.example.StockValueApp.model.DividendDiscountModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DividendDiscountMappingService {

    public DividendDiscountModel mapToEntity(final DividendDiscountRequestDTO requestDTO){
        return DividendDiscountModel.builder()
                .companyName(requestDTO.getCompanyName())
                .ticker(requestDTO.getTicker())
                .currentYearsDiv(requestDTO.getCurrentYearsDiv())
                .valueOfNextYearsDiv(calculateValueOfNextYearsDiv(requestDTO))
                .wacc(requestDTO.getWacc())
                .expectedGrowthRate(requestDTO.getExpectedGrowthRate())
                .intrinsicValue(calculateDividendDiscountValue(requestDTO))
                .build();
    }

    public List<DividendDiscountResponseDTO> mapToResponse(List<DividendDiscountModel> dividendDiscountValuations){
        List<DividendDiscountResponseDTO> mappedDividendValuations = new ArrayList<>();
        for (DividendDiscountModel valuation : dividendDiscountValuations) {
            DividendDiscountResponseDTO dto = new DividendDiscountResponseDTO();
            dto.setCompanyName(valuation.getCompanyName());
            dto.setTicker(valuation.getTicker());
            dto.setCurrentYearsDiv(valuation.getCurrentYearsDiv());
            dto.setValueOfNextYearsDiv(valuation.getValueOfNextYearsDiv());
            dto.setWacc(valuation.getWacc());
            dto.setExpectedGrowthRate(valuation.getExpectedGrowthRate());
            dto.setIntrinsicValue(valuation.getIntrinsicValue());
            dto.setCreationDate(valuation.getCreationDate());
            mappedDividendValuations.add(dto);
        }
        return mappedDividendValuations;
    }

    private Double calculateDividendDiscountValue(DividendDiscountRequestDTO requestDTO){
        return roundToTwoDecimal(calculateValueOfNextYearsDiv(requestDTO)
                / (convertToPercentages(requestDTO.getWacc()) - convertToPercentages(requestDTO.getExpectedGrowthRate())));
    }

    private Double roundToTwoDecimal(final Double numberToRound){
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double convertToPercentages(Double number){
        return number / 100;
    }

    private Double calculateValueOfNextYearsDiv(DividendDiscountRequestDTO requestDTO){
        return roundToTwoDecimal(requestDTO.getCurrentYearsDiv() * (1 + convertToPercentages(requestDTO.getExpectedGrowthRate())));
    }

}
