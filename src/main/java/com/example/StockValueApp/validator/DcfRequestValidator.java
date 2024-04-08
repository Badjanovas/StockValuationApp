package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.repository.DcfModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DcfRequestValidator {

    private final DcfModelRepository dcfModelRepository;

    public void validateDcfModelRequest(final DcfModelRequestDTO dcfModelRequestDTO) throws MandatoryFieldsMissingException {
        if (dcfModelRequestDTO == null) {
            log.error("Request was empty.");
            throw new MandatoryFieldsMissingException("Request was empty.");
        } else if (dcfModelRequestDTO.getCompanyName() == null) {
            log.error("Mandatory company name field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company name field is empty.");
        } else if (dcfModelRequestDTO.getCompanyName().isBlank()) {
            log.error("Mandatory company ticker field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is missing.");
        } else if (dcfModelRequestDTO.getTicker() == null) {
            log.error("Mandatory company ticker field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is missing.");
        } else if (dcfModelRequestDTO.getTicker().isBlank()) {
            log.error("Mandatory company ticker field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is empty.");
        } else if (dcfModelRequestDTO.getSumOfFCF() == null) {
            log.error("Mandatory sumOfFCF field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory sumOfFCF field is empty.");
        } else if (dcfModelRequestDTO.getCashAndCashEquivalents() == null) {
            log.error("Mandatory cashAndCashEquivalents field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory cashAndCashEquivalents field is empty.");
        } else if (dcfModelRequestDTO.getTotalDebt() == null) {
            log.error("Mandatory totalDebt field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory totalDebt field is empty.");
        } else if (dcfModelRequestDTO.getSharesOutstanding() == null) {
            log.error("Mandatory sharesOutstanding field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory sharesOutstanding field is empty.");
        }
    }

    public void validateDcfModelList(List<DcfModel> dcfValuations) throws NoDcfValuationsFoundException {
        if (dcfValuations.isEmpty()){
            log.error("No discounted cash flow valuations found.");
            throw new NoDcfValuationsFoundException("No discounted cash flow valuations found.");
        }
    }

}
