package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.dto.DividendDiscountResponseDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDividendDiscountModelFoundException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
import com.example.StockValueApp.model.DividendDiscountModel;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.repository.DividendDiscountRepository;
import com.example.StockValueApp.service.mappingService.DividendDiscountMappingService;
import com.example.StockValueApp.validator.DividendDiscountRequestValidator;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Data
public class DividendDiscountService {

    private final DividendDiscountRepository dividendDiscountRepository;
    private final DividendDiscountMappingService dividendDiscountMappingService;
    private final DividendDiscountRequestValidator dividendDiscountRequestValidator;
    private final GlobalExceptionValidator globalExceptionValidator;

    public List<DividendDiscountModel> getAllDividendDiscountValuations() throws NoDividendDiscountModelFoundException {
        final List<DividendDiscountModel> dividendDiscountValuations = dividendDiscountRepository.findAll();
        dividendDiscountRequestValidator.validateDividendDiscountList(dividendDiscountValuations);

        log.info(dividendDiscountValuations.size() + " Dividend discount valuations were found in DB.");
        return dividendDiscountValuations;
    }

    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByTicker(final String ticker) throws NoDividendDiscountModelFoundException {
        final List<DividendDiscountModel> companiesValuations = dividendDiscountRepository.findByTickerIgnoreCase(ticker);
        dividendDiscountRequestValidator.validateDividendDiscountList(companiesValuations, ticker);

        log.info("Found " + companiesValuations.size() + " Dividend discount company valuations with ticker: " + ticker);
        return dividendDiscountMappingService.mapToResponse(companiesValuations);
    }

    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByCompanyName(final String companyName) throws NoDividendDiscountModelFoundException {
        final List<DividendDiscountModel> companiesValuations = dividendDiscountRepository.findByCompanyNameIgnoreCase(companyName);
        dividendDiscountRequestValidator.validateDividendDiscountList(companiesValuations, companyName);

        log.info("Found " + companiesValuations.size() + " Dividend discount company valuations with name: " + companyName);
        return dividendDiscountMappingService.mapToResponse(companiesValuations);
    }

    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByDate(final LocalDate date) throws NoDividendDiscountModelFoundException {
        final List<DividendDiscountModel> valuationsByDate = dividendDiscountRepository.findByCreationDate(date);
        dividendDiscountRequestValidator.validateDividendDiscountList(valuationsByDate, date);

        log.info("Found " + valuationsByDate.size() + " Dividend discount valuations made at: " + date);
        return dividendDiscountMappingService.mapToResponse(valuationsByDate);
    }

    public List<DividendDiscountResponseDTO> addDividendDiscountValuation(final DividendDiscountRequestDTO dividendDiscountRequestDTO) throws MandatoryFieldsMissingException {
        dividendDiscountRequestValidator.validateDividendDiscountRequest(dividendDiscountRequestDTO);
        final DividendDiscountModel dividendDiscountModel = dividendDiscountMappingService.mapToEntity(dividendDiscountRequestDTO);
        dividendDiscountRepository.save(dividendDiscountModel);

        log.info("Calculation created successfully.");
        return dividendDiscountMappingService.mapToResponse(dividendDiscountRepository.findAll());
    }

    public void deleteDividendDiscountValuationById(final Long id) throws NotValidIdException, NoDividendDiscountModelFoundException {
        globalExceptionValidator.validateId(id);
        dividendDiscountRequestValidator.validateDividendDiscountById(id);
        dividendDiscountRepository.deleteById(id);

        log.info("Dividend discount valuation  with id number " + id + " was deleted from DB successfully.");
    }


}
