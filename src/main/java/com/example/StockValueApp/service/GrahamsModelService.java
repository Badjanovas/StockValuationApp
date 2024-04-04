package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.dto.GrahamsResponseDTO;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.repository.GrahamsValuationRepo;
import com.example.StockValueApp.service.mappingService.GrahamsModelMappingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Data
public class GrahamsModelService {

    private final GrahamsValuationRepo grahamsRepository;
    private final GrahamsModelMappingService grahamsModelMappingService;


    public List<GrahamsResponseDTO> saveGrahamsValuation(final GrahamsRequestDTO grahamsRequestDTO){
        final GrahamsModel grahamsModel = grahamsModelMappingService.mapToEntity(grahamsRequestDTO);
        grahamsRepository.save(grahamsModel);
        log.info("Calculation created successfully.");
        return grahamsModelMappingService.mapToResponse(grahamsRepository.findAll());
    }

    public List<GrahamsModel> getAllGrahamsValuations(){
        List<GrahamsModel> grahamsValuations = new ArrayList<>();
        grahamsValuations.addAll(grahamsRepository.findAll());
        return grahamsValuations;
    }



}
