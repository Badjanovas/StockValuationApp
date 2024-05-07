package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.dto.DcfModelResponseDTO;
import com.example.StockValueApp.model.DcfModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DcfModelMappingServiceTest {

    DcfModelMappingService service = new DcfModelMappingService();

    @Test
    void testMapToEntity_CorrectlyMapsRequestDTOToEntity(){

        DcfModelRequestDTO requestDTO = new
                DcfModelRequestDTO("Starbuks", "sbux", 71250.0,3380.0, 22400.46, 1130.0);

        DcfModel result = service.mapToEntity(requestDTO);

        assertEquals("Starbuks", result.getCompanyName());
        assertEquals("sbux", result.getTicker());
        assertEquals(71250.0, result.getSumOfFCF());
        assertEquals(3380.0, result.getCashAndCashEquivalents());
        assertEquals(22400.46, result.getTotalDebt());
        assertEquals(1130.0, result.getSharesOutstanding());
        assertEquals(52229.54, result.getEquityValue()); //71250 + 3380 - 22400.46
        assertEquals(46.22, result.getIntrinsicValue()); //52229.54 / 1130
    }

    @Test
    void testMapToResponse_CorrectlyMapsEntityListToResponseDTOList(){

        List<DcfModel> dcfModels = Arrays.asList(
                new DcfModel("Starbuks", "sbux", 71250.0,3380.0, 22400.46, 1130.0, 52229.54, 46.22, null),
                new DcfModel("PayPal", "pypl", 81817.0, 21540.0, 61115.0, 1070.0,42242.0, 39.48, null)
        );

        List<DcfModelResponseDTO> result = service.mapToResponse(dcfModels);

        assertEquals(2, result.size());
        assertEquals("Starbuks",  result.get(0).getCompanyName());
        assertEquals(46.22, result.get(0).getIntrinsicValue());
        assertEquals("PayPal", result.get(1).getCompanyName());
        assertEquals(39.48, result.get(1).getIntrinsicValue());
    }


}