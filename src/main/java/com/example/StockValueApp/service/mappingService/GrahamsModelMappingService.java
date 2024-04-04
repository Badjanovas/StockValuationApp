package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.dto.GrahamsResponseDTO;
import com.example.StockValueApp.model.GrahamsModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrahamsModelMappingService {

    public GrahamsModel mapToEntity(final GrahamsRequestDTO requestDTO){
        return GrahamsModel.builder()
                .name(requestDTO.getCompanyName())
                .ticker(requestDTO.getCompanyTicker())
                .eps(requestDTO.getEps())
                .growthRate(requestDTO.getGrowthRate())
                .currentYieldOfBonds(requestDTO.getCurrentYieldOfBonds())
                .intrinsicValue(calculateGrahamsValuation(requestDTO))
                .build();
    }

    public List<GrahamsResponseDTO> mapToResponse(List<GrahamsModel> grahamModelCalculations){
        List<GrahamsResponseDTO> mappedGrahamCalculations = new ArrayList<>();
        for (GrahamsModel calculation : grahamModelCalculations) {
            GrahamsResponseDTO dto = new GrahamsResponseDTO();
            dto.setCompanyName(calculation.getName());
            dto.setCompanyTicker(calculation.getTicker());
            dto.setEps(calculation.getEps());
            dto.setGrowthRate(calculation.getGrowthRate());
            dto.setCurrentYieldOfBonds(calculation.getCurrentYieldOfBonds());
            dto.setIntrinsicValue(calculation.getIntrinsicValue());
            dto.setCreationDate(calculation.getCreationDate());
            mappedGrahamCalculations.add(dto);
        }
        return mappedGrahamCalculations;
    }

    //Grahams model formula
    private Double calculateGrahamsValuation(final GrahamsRequestDTO model){
       return roundToTwoDecimal((model.getEps() *
                (GrahamsModel.BASE_PE +
                        model.getGrowthRate()) *
                GrahamsModel.AVERAGE_YIELD_AAA_BONDS) / model.getCurrentYieldOfBonds());
    }

    private Double roundToTwoDecimal(final Double numberToRound){
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
