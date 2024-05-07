package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.dto.DividendDiscountResponseDTO;
import com.example.StockValueApp.model.DividendDiscountModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DividendDiscountMappingServiceTest {

    DividendDiscountMappingService service = new DividendDiscountMappingService();

    @Test
    void testMapToEntity_CorrectlyMapsRequestDTOToEntity(){

        DividendDiscountRequestDTO requestDTO =
                new DividendDiscountRequestDTO("Nestle", "nesn.sw", 3.41,5.69, 3.5);

        DividendDiscountModel result = service.mapToEntity(requestDTO);

        assertEquals("Nestle", result.getCompanyName());
        assertEquals("nesn.sw", result.getTicker());
        assertEquals(3.41, result.getCurrentYearsDiv());
        assertEquals(3.53,result.getValueOfNextYearsDiv()); // 3.41 * 1.035
        assertEquals(5.69, result.getWacc());
        assertEquals(3.5, result.getExpectedGrowthRate());
        assertEquals(161.19, result.getIntrinsicValue()); // 3.53 / (0.0569 - 0.035)
    }

    @Test
    void testMapToResponse_CorrectlyMapsEntityListToResponseDTOList() {

        List<DividendDiscountModel> dividendDiscountModels = Arrays.asList(
                new DividendDiscountModel("Nestle", "nesn.sw", 3.53,5.69, 3.5, 161.19, null),
                new DividendDiscountModel("Starbuks", "sbux", 2.44, 8.11, 7.0,219.82, null)
        );

        List<DividendDiscountResponseDTO> result = service.mapToResponse(dividendDiscountModels);

        assertEquals(2,result.size());
        assertEquals("Nestle", result.get(0).getCompanyName());
        assertEquals(161.19, result.get(0).getIntrinsicValue());
        assertEquals("Starbuks", result.get(1).getCompanyName());
        assertEquals(219.82, result.get(1).getIntrinsicValue());
    }

}