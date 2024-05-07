package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.dto.GrahamsResponseDTO;
import com.example.StockValueApp.model.GrahamsModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GrahamsModelMappingServiceTest {

    GrahamsModelMappingService service = new GrahamsModelMappingService();

    @Test
    void testMapToEntity_CorrectlyMapsRequestDTOToEntity(){

        GrahamsRequestDTO requestDTO =
                new GrahamsRequestDTO("Alibaba","baba",6.43,10.0,5.06);

        GrahamsModel result = service.mapToEntity(requestDTO);

        assertEquals("Alibaba", result.getName());
        assertEquals("baba", result.getTicker());
        assertEquals(6.43, result.getEps());
        assertEquals(10.0, result.getGrowthRate());
        assertEquals(5.06, result.getCurrentYieldOfBonds());
        assertEquals(95.05, result.getIntrinsicValue()); // (6.43 * (7 + 10) *4.4)/5.06
    }

    @Test
    void testMapToResponse_CorrectlyMapsEntityListToResponseDTOList(){
        List<GrahamsModel> grahamsModels = Arrays.asList(
                new GrahamsModel("Alibaba","baba",6.43,10.0,5.06,95.05, null),
                new GrahamsModel("Apple", "aapl", 6.43, 12.0, 5.09, 106.23, null)
        );

        List<GrahamsResponseDTO> result = service.mapToResponse(grahamsModels);

        assertEquals(2, result.size());
        assertEquals("Alibaba", result.get(0).getCompanyName());
        assertEquals(95.05, result.get(0).getIntrinsicValue());
        assertEquals("Apple", result.get(1).getCompanyName());
        assertEquals(106.23, result.get(1).getIntrinsicValue());

    }

}