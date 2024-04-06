package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.dto.GrahamsResponseDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NotValidIdException;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.repository.GrahamsModelRepository;
import com.example.StockValueApp.service.mappingService.GrahamsModelMappingService;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import com.example.StockValueApp.validator.GrahamsModelRequestValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Data
public class GrahamsModelService {

    private final GrahamsModelRepository grahamsRepository;
    private final GrahamsModelMappingService grahamsModelMappingService;
    private final GlobalExceptionValidator globalExceptionValidator;
    private final GrahamsModelRequestValidator grahamsModelRequestValidator;


    public List<GrahamsResponseDTO> addGrahamsValuation(final GrahamsRequestDTO grahamsRequestDTO) throws MandatoryFieldsMissingException {
        grahamsModelRequestValidator.validateGrahamsModelRequest(grahamsRequestDTO);
        final GrahamsModel grahamsModel = grahamsModelMappingService.mapToEntity(grahamsRequestDTO);
        grahamsRepository.save(grahamsModel);
        log.info("Calculation created successfully.");
        return grahamsModelMappingService.mapToResponse(grahamsRepository.findAll());
    }

    public List<GrahamsModel> getAllGrahamsValuations(){
        List<GrahamsModel> grahamsValuations = new ArrayList<>();
        grahamsValuations.addAll(grahamsRepository.findAll());
        log.info(grahamsValuations.size() + " Grahams valuations were found in DB.");
        return grahamsValuations;
    }

    // sita gal geriau daryti void nes gali buti daug skaiciavimu tad visada grazinant visus po istrinimo bus apkrauta sistema??
    public List<GrahamsResponseDTO> deleteGrahamsModelById(final Long id) throws NotValidIdException, NoGrahamsModelFoundException {
        globalExceptionValidator.validateId(id);
        grahamsModelRequestValidator.validateGrahamsModelById(id);
        grahamsRepository.deleteById(id);
        log.info("UGrahams valuation  with id number " + id + " was deleted from DB successfully.");

        return grahamsModelMappingService.mapToResponse(grahamsRepository.findAll());
    }



}
