package com.example.StockValueApp.service;

import com.example.StockValueApp.model.GrahamsModule;
import com.example.StockValueApp.repository.GrahamsValuationRepo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
class GrahamsValuationServiceTest {

    @Test
    public void testCalculateGrahamsValuation(){
        GrahamsValuationService grahamsValuationService = new GrahamsValuationService();
        GrahamsModule module = new GrahamsModule("baba",6.43,10.0,5.06);

        Double result = grahamsValuationService.calculateGrahamsValuation(module);

        double expectedValue = (6.43 * (GrahamsModule.BASE_PE + 10.0) * GrahamsModule.AVERAGE_YIELD_AAA_BONDS)/5.06;
        expectedValue = BigDecimal.valueOf(expectedValue)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();

        assertEquals(expectedValue, result);
        assertEquals(expectedValue, module.getIntrinsicValue());
    }

}