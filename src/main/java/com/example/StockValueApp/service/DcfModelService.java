package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.dto.DcfModelResponseDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.repository.DcfModelRepository;
import com.example.StockValueApp.service.mappingService.DcfModelMappingService;
import com.example.StockValueApp.validator.DcfRequestValidator;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Data
public class DcfModelService {

    private final DcfModelRepository dcfModelRepository;
    private final DcfModelMappingService dcfModelMappingService;
    private final GlobalExceptionValidator globalExceptionValidator;
    private final DcfRequestValidator dcfRequestValidator;

    public List<DcfModel> getAllDcfValuations() throws NoDcfValuationsFoundException {
        final List<DcfModel> dcfValuations = dcfModelRepository.findAll();
        dcfRequestValidator.validateDcfModelList(dcfValuations);

        log.info(dcfValuations.size() + " Grahams valuations were found in DB.");
        return dcfValuations;
    }
// turbut reikes dateti useri kaip parametra kad butu paimamos tik prisiloginusio userio stock valuations.
    public List<DcfModelResponseDTO> getDcfValuationsByTicker(final String ticker) throws NoDcfValuationsFoundException {
        final List<DcfModel> companiesValuations = dcfModelRepository.findByTickerIgnoreCase(ticker);
        dcfRequestValidator.validateDcfModelList(companiesValuations, ticker);

        log.info("Found " + companiesValuations.size() + " Discounted cash flow company valuations with ticker: " + ticker);
        return dcfModelMappingService.mapToResponse(companiesValuations);
    }

    public List<DcfModelResponseDTO> getDcfValuationsByCompanyName(final String companyName) throws NoDcfValuationsFoundException {
        final List<DcfModel> companiesValuations = dcfModelRepository.findByCompanyNameIgnoreCase(companyName);
        dcfRequestValidator.validateDcfModelList(companiesValuations, companyName);

        log.info("Found " + companiesValuations.size() + " Discounted cash flow company valuations with ticker: " + companyName);
        return dcfModelMappingService.mapToResponse(companiesValuations);
    }

    public List<DcfModelResponseDTO> getDcfValuationByDate(final LocalDate date) throws NoGrahamsModelFoundException {
        final List<DcfModel> valuationsByDate = dcfModelRepository.findByCreationDate(date);
        dcfRequestValidator.validateDcfModelList(valuationsByDate, date);

        log.info("Found " + valuationsByDate.size() + " Discounted cash flow valuations made at: " + date);
        return dcfModelMappingService.mapToResponse(valuationsByDate);
    }

    public List<DcfModelResponseDTO> addDcfValuation(final DcfModelRequestDTO dcfModelRequestDTO) throws MandatoryFieldsMissingException {
        dcfRequestValidator.validateDcfModelRequest(dcfModelRequestDTO);
        final DcfModel dcfModel = dcfModelMappingService.mapToEntity(dcfModelRequestDTO);
        dcfModelRepository.save(dcfModel);

        log.info("Calculation created successfully.");
        return dcfModelMappingService.mapToResponse(dcfModelRepository.findAll());
    }

    public void deleteDcfValuationById(final Long id) throws NotValidIdException, NoDcfValuationsFoundException {
        globalExceptionValidator.validateId(id);
        dcfRequestValidator.validateDcfModelById(id);
        dcfModelRepository.deleteById(id);

        log.info("Discounted cash flow valuation  with id number " + id + " was deleted from DB successfully.");
    }

}
