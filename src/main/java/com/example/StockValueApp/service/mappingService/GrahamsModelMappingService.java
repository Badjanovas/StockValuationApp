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

    public GrahamsModel mapToEntity(final GrahamsRequestDTO requestDTO) {
        return GrahamsModel.builder()
                .name(requestDTO.getCompanyName())
                .ticker(requestDTO.getCompanyTicker())
                .eps(requestDTO.getEps())
                .growthRate(requestDTO.getGrowthRate())
                .currentYieldOfBonds(requestDTO.getCurrentYieldOfBonds())
                .intrinsicValue(calculateGrahamsValuation(requestDTO))
                .build();
    }

    public List<GrahamsResponseDTO> mapToResponse(List<GrahamsModel> grahamModelValuations) {
        List<GrahamsResponseDTO> mappedGrahamValuations = new ArrayList<>();
        for (GrahamsModel grahamsValuation : grahamModelValuations) {
            GrahamsResponseDTO dto = GrahamsResponseDTO.builder()
                    .companyName(grahamsValuation.getName())
                    .companyTicker(grahamsValuation.getTicker())
                    .eps(grahamsValuation.getEps())
                    .growthRate(grahamsValuation.getGrowthRate())
                    .currentYieldOfBonds(grahamsValuation.getCurrentYieldOfBonds())
                    .intrinsicValue(grahamsValuation.getIntrinsicValue())
                    .creationDate(grahamsValuation.getCreationDate())
                    .build();

            mappedGrahamValuations.add(dto);
        }
        return mappedGrahamValuations;
    }

    private Double calculateGrahamsValuation(final GrahamsRequestDTO requestDTO) {
        return roundToTwoDecimal((requestDTO.getEps() *
                (GrahamsModel.BASE_PE + requestDTO.getGrowthRate()) *
                GrahamsModel.AVERAGE_YIELD_AAA_BONDS) / requestDTO.getCurrentYieldOfBonds());
    }

    private Double roundToTwoDecimal(final Double numberToRound) {
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
