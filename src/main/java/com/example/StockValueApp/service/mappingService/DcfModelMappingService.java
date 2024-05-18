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

    public DcfModel mapToEntity(final DcfModelRequestDTO requestDTO) {
        double wacc = convertToPercentages(requestDTO.getWacc());
        double growthRate = convertToPercentages(requestDTO.getGrowthRate());

        double sumOfDiscountedFCF = calculateSumOfDiscountedFCF(requestDTO.getSumOfFCF(), wacc, growthRate);
        double equityValue = sumOfDiscountedFCF + requestDTO.getCashAndCashEquivalents() - requestDTO.getTotalDebt();

        return DcfModel.builder()
                .companyName(requestDTO.getCompanyName())
                .ticker(requestDTO.getCompanyTicker())
                .sumOfFCF(requestDTO.getSumOfFCF())
                .cashAndCashEquivalents(requestDTO.getCashAndCashEquivalents())
                .totalDebt(requestDTO.getTotalDebt())
                .equityValue(roundToTwoDecimal(equityValue))
                .sharesOutstanding(requestDTO.getSharesOutstanding())
                .intrinsicValue(calculateDcfValuation(equityValue, requestDTO.getSharesOutstanding()))
                .build();
    }

    public List<DcfModelResponseDTO> mapToResponse(final List<DcfModel> dcfValuations) {
        final List<DcfModelResponseDTO> mappedDcfValuations = new ArrayList<>();
        for (DcfModel dcfValuation : dcfValuations) {
            DcfModelResponseDTO dto = DcfModelResponseDTO.builder()
                    .id(dcfValuation.getId())
                    .companyName(dcfValuation.getCompanyName())
                    .companyTicker(dcfValuation.getTicker())
                    .sumOfFCF(dcfValuation.getSumOfFCF())
                    .cashAndCashEquivalents(dcfValuation.getCashAndCashEquivalents())
                    .totalDebt(dcfValuation.getTotalDebt())
                    .equityValue(dcfValuation.getEquityValue())
                    .sharesOutstanding(dcfValuation.getSharesOutstanding())
                    .intrinsicValue(dcfValuation.getIntrinsicValue())
                    .creationDate(dcfValuation.getCreationDate())
                    .build();

            mappedDcfValuations.add(dto);
        }
        return mappedDcfValuations;
    }

    public DcfModelResponseDTO mapToResponse(final DcfModel dcfValuation){
        return DcfModelResponseDTO.builder()
                .id(dcfValuation.getId())
                .companyName(dcfValuation.getCompanyName())
                .companyTicker(dcfValuation.getTicker())
                .sumOfFCF(dcfValuation.getSumOfFCF())
                .cashAndCashEquivalents(dcfValuation.getCashAndCashEquivalents())
                .totalDebt(dcfValuation.getTotalDebt())
                .equityValue(dcfValuation.getEquityValue())
                .sharesOutstanding(dcfValuation.getSharesOutstanding())
                .intrinsicValue(dcfValuation.getIntrinsicValue())
                .creationDate(dcfValuation.getCreationDate())
                .build();
    }

    private Double calculateSumOfDiscountedFCF(Double sumOfFCF, Double wacc, Double growthRate) {
        // Assume initial FCF is the average of sumOfFCF divided by 5 for simplicity
        double initialFCF = sumOfFCF / 5;
        System.out.println("Initial FCF: " + initialFCF);
        double sumOfDiscountedFCF = 0.0;

        // Calculate FCF for each of the next 5 years and discount them to present value
        for (int i = 1; i <= 5; i++) {
            double fcf = initialFCF * Math.pow(1 + growthRate, i);
            double discountedFCF = fcf / Math.pow(1 + wacc, i);
            sumOfDiscountedFCF += discountedFCF;
            System.out.println("Year " + i + " FCF: " + fcf + " Discounted FCF: " + discountedFCF);
        }

        // Calculate Terminal Value
        double terminalValue = initialFCF * Math.pow(1 + growthRate, 5) * (1 + growthRate) / (wacc - growthRate);
        double discountedTerminalValue = terminalValue / Math.pow(1 + wacc, 5);
        System.out.println("Terminal Value: " + terminalValue + " Discounted Terminal Value: " + discountedTerminalValue);

        // Total Enterprise Value
        double totalEnterpriseValue = sumOfDiscountedFCF + discountedTerminalValue;

        return totalEnterpriseValue;
    }

    private Double convertToPercentages(Double number) {
        return number / 100;
    }

    private Double calculateDcfValuation(Double equityValue, Double sharesOutstanding) {
        return roundToTwoDecimal(equityValue / sharesOutstanding);
    }

    private Double roundToTwoDecimal(final Double numberToRound) {
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
