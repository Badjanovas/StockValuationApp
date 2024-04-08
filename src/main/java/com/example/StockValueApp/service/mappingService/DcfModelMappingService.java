package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.dto.DcfModelResponseDTO;
import com.example.StockValueApp.model.DcfModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DcfModelMappingService {

    public DcfModel mapToEntity(final DcfModelRequestDTO requestDTO){
        return DcfModel.builder()
                .companyName(requestDTO.getCompanyName())
                .ticker(requestDTO.getTicker())
                .sumOfFCF(requestDTO.getSumOfFCF())
                .cashAndCashEquivalents(requestDTO.getCashAndCashEquivalents())
                .totalDebt(requestDTO.getTotalDebt())
                .equityValue(calculateEquityValue(requestDTO))
                .sharesOutstanding(requestDTO.getSharesOutstanding())
                .intrinsicValue(calculateDcfValuation(requestDTO))
                .build();
    }

    public List<DcfModelResponseDTO> mapToResponse(List<DcfModel> dcfValuations){
        List<DcfModelResponseDTO> mappedDcfValuations = new ArrayList<>();
        for (DcfModel dcfValuation : dcfValuations) {
            DcfModelResponseDTO dto = new DcfModelResponseDTO();
            dto.setCompanyName(dcfValuation.getCompanyName());
            dto.setTicker(dcfValuation.getTicker());
            dto.setSumOfFCF(dcfValuation.getSumOfFCF());
            dto.setCashAndCashEquivalents(dcfValuation.getCashAndCashEquivalents());
            dto.setTotalDebt(dcfValuation.getTotalDebt());
            dto.setEquityValue(dcfValuation.getEquityValue());
            dto.setSharesOutstanding(dcfValuation.getSharesOutstanding());
            dto.setIntrinsicValue(dcfValuation.getIntrinsicValue());
            dto.setCreationDate(dcfValuation.getCreationDate());
            mappedDcfValuations.add(dto);
        }
        return mappedDcfValuations;
    }

    private Double calculateEquityValue(final DcfModelRequestDTO requestDTO){
        return requestDTO.getSumOfFCF() + requestDTO.getCashAndCashEquivalents() - requestDTO.getTotalDebt();
    }

    private Double calculateDcfValuation(final DcfModelRequestDTO requestDTO){
        return roundToTwoDecimal(calculateEquityValue(requestDTO) / requestDTO.getSharesOutstanding());
    }

    private Double roundToTwoDecimal(final Double numberToRound){
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
