package com.example.StockValueApp.service;

import com.example.StockValueApp.model.GrahamsModule;
import com.example.StockValueApp.repository.GrahamsValuationRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Data
public class GrahamsValuationService {

    private GrahamsValuationRepo repository;


    public List<GrahamsModule> saveGrahamsValuation(final GrahamsModule grahamsModule){
        repository.save(grahamsModule);
        return repository.findAll();
    }

    public List<GrahamsModule> getAllGrahamsValuations(){
        List<GrahamsModule> grahamsValuations = new ArrayList<>();
        grahamsValuations.addAll(repository.findAll());
        return grahamsValuations;
    }

    public Double calculateGrahamsValuation(final GrahamsModule module){
           module.setIntrinsicValue(roundToTwoDecimal((module.getEps() *
                   (GrahamsModule.BASE_PE +
                module.getGrowthRate()) *
                GrahamsModule.AVERAGE_YIELD_AAA_BONDS) / module.getCurrentYieldOfBonds()));

           return module.getIntrinsicValue();
    }

    private Double roundToTwoDecimal(final Double numberToRound){
        return BigDecimal.valueOf(numberToRound)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
