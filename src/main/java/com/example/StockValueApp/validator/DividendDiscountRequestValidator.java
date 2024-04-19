package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.DividendDiscountModel;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.repository.DividendDiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DividendDiscountRequestValidator {

    private final DividendDiscountRepository dividendDiscountRepository;

    public void validateDividendDiscountRequest(final DividendDiscountRequestDTO dividendDiscountRequestDTO) throws MandatoryFieldsMissingException {
        if (dividendDiscountRequestDTO == null){
            log.error("Request was empty.");
            throw new MandatoryFieldsMissingException("Request was empty.");
        } else if (dividendDiscountRequestDTO.getCompanyName() == null) {
            log.error("Mandatory company name field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company name field is missing.");
        } else if (dividendDiscountRequestDTO.getCompanyName().isEmpty()) {
            log.error("Mandatory company name field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company name field is empty.");
        } else if (dividendDiscountRequestDTO.getTicker() == null ) {
            log.error("Mandatory company ticker field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is missing.");
        } else if (dividendDiscountRequestDTO.getTicker().isBlank()) {
            log.error("Mandatory company ticker field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is empty.");
        } else if (dividendDiscountRequestDTO.getCurrentYearsDiv() == null) {
            log.error("Mandatory currentYearsDiv field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory currentYearsDiv field is empty.");
        } else if (dividendDiscountRequestDTO.getWacc() == null) {
            log.error("Mandatory wacc field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory wacc field is empty.");
        } else if (dividendDiscountRequestDTO.getExpectedGrowthRate() == null) {
            log.error("Mandatory expectedGrowthRate field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory expectedGrowthRate field is empty.");
        }
    }

    public void validateDividendDiscountList(final List<DividendDiscountModel> dividendDiscountModels) throws NoDividendDiscountModelFoundException {
        if (dividendDiscountModels.isEmpty()){
            log.error("No dividend discount valuations found for: ");
            throw new NoDividendDiscountModelFoundException("No dividend discount valuations found for: ");
        }
    }

    public void validateDividendDiscountList(final List<DividendDiscountModel> dividendDiscountModels, String companyNameOrTicker) throws NoDividendDiscountModelFoundException {
        if (dividendDiscountModels.isEmpty()){
            log.error("No dividend discount valuations found for: " + companyNameOrTicker + ".");
            throw new NoDividendDiscountModelFoundException("No dividend discount valuations found for: " + companyNameOrTicker + ".");
        }
    }

    public void validateDividendDiscountList(final List<DividendDiscountModel> dividendDiscountModels, LocalDate date) throws NoDividendDiscountModelFoundException {
        if (dividendDiscountModels.isEmpty()){
            log.error("There are no valuations made in " + date);
            throw new NoDividendDiscountModelFoundException("There are no valuations made in " + date);
        }
    }

    public void validateDividendDiscountById(final Long id) throws NoDividendDiscountModelFoundException {
        if (!dividendDiscountRepository.existsById(id)){
            log.error("Dividend discount valuation with id number " + id + " not found.");
            throw new NoDividendDiscountModelFoundException("Dividend discount valuation with id number " + id + " not found.");
        }
    }

    public void validateExpectedGrowthRateInput(final Double wacc, final Double expectedGrowthRate) throws IncorrectCompaniesExpectedGrowthException {
        if (expectedGrowthRate >= wacc){
            log.error("Dividend discount model isn't suitable to calculate intrinsic value if expected growth rate is higher or equal to the weighted average cost of capital.");
            throw new IncorrectCompaniesExpectedGrowthException("Dividend discount model isn't suitable to calculate intrinsic value if expected growth rate is higher or equal to the weighted average cost of capital.");
        }
    }

    public void validateDividendDiscountModelForUser(final Long valuationId, final Long userId) throws ValuationDoestExistForSelectedUser {
        Optional<DividendDiscountModel> valuation = dividendDiscountRepository.findById(valuationId);
        if (!valuation.isPresent() || !valuation.get().getUser().getId().equals(userId)){
            log.error("Valuation does not exist for this user");
            throw new ValuationDoestExistForSelectedUser("Valuation does not exist for this user");
        }
    }

}
