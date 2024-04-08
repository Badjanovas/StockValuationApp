package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.dto.DcfModelResponseDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.repository.DcfModelRepository;
import com.example.StockValueApp.service.mappingService.DcfModelMappingService;
import com.example.StockValueApp.validator.DcfRequestValidator;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        final List<DcfModel> dcfValuations = new ArrayList<>();
        dcfValuations.addAll(dcfModelRepository.findAll());
        dcfRequestValidator.validateDcfModelList(dcfValuations);

        log.info(dcfValuations.size() + " Grahams valuations were found in DB.");
        return dcfValuations;
    }

    public List<DcfModelResponseDTO> addDcfValuation(final DcfModelRequestDTO dcfModelRequestDTO) throws MandatoryFieldsMissingException {
        dcfRequestValidator.validateDcfModelRequest(dcfModelRequestDTO);
        final DcfModel dcfModel = dcfModelMappingService.mapToEntity(dcfModelRequestDTO);
        dcfModelRepository.save(dcfModel);

        log.info("Calculation created successfully.");
        return  dcfModelMappingService.mapToResponse(dcfModelRepository.findAll());
    }

}
