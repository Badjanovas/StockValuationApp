package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDcfValuationsFoundException;
import com.example.StockValueApp.exception.ValuationDoestExistForSelectedUserException;
import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.DcfModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DcfRequestValidatorTest {

    @Mock
    private DcfModelRepository dcfModelRepository;

    @InjectMocks
    private DcfRequestValidator validator;

    /* Tests for validateDcfModelRequest */
    @Test
    void validateDcfModelRequest_NullRequest_ThrowsException() {
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(null)
        );
        assertEquals("Request was empty.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_CompanyNameNull_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                null,
                "ticker",
                1.1,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory company name field is missing.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_CompanyNameIsBlank_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "",
                "ticker",
                1.1,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory company name field is empty.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_CompanyTickerIsNull_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "company",
                null,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory company ticker field is missing.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_CompanyTickerIsBlank_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "company",
                "",
                1.1,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory company ticker field is empty.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_SumOfFCFIsNull_ThrowsException() {
        var requestDto = new DcfModelRequestDTO(
                "company",
                "ticker",
                null,
                1.1,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDto)
        );
        assertEquals("Mandatory sumOfFCF field is missing.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_CashAndCashEquivalentsIsNull_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "company",
                "ticker",
                1.1,
                null,
                1.1,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory cashAndCashEquivalents field is missing.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_TotalDebtIsNull_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "company",
                "ticker",
                1.1,
                1.1,
                null,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory totalDebt field is missing.", exception.getMessage());
    }

    @Test
    void validateDcfModelRequest_SharesOutstandingIsNull_ThrowsException() {
        var requestDTO = new DcfModelRequestDTO(
                "company",
                "ticker",
                1.1,
                1.1,
                1.1,
                null,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDcfModelRequest(requestDTO)
        );
        assertEquals("Mandatory sharesOutstanding field is missing.", exception.getMessage());
    }

    /* Tests for validateDcfModelList */
    @Test
    void validateDcfModelList_EmptyList_ThrowsException() {
        List<DcfModel> valuations = new ArrayList<>();

        NoDcfValuationsFoundException exception = assertThrows(
                NoDcfValuationsFoundException.class,
                () -> validator.validateDcfModelList(valuations)
        );

        assertEquals("No discounted cash flow valuations found.", exception.getMessage());
    }

    @Test
    void validateDcfModelList_PopulatedList_DoesNotThrowException() {
        List<DcfModel> valuations = new ArrayList<>();
        valuations.add(new DcfModel());
        valuations.add(new DcfModel());
        valuations.add(new DcfModel());

        assertDoesNotThrow(() -> validator.validateDcfModelList(valuations));
    }

    /* Tests for validateDcfModelById */
    @Test
    void validateDcfModelById_DoesNotExist_ThrowsException() {
        Long valuationId = 1L;

        NoDcfValuationsFoundException exception = assertThrows(
                NoDcfValuationsFoundException.class,
                () -> validator.validateDcfModelById(valuationId)
        );

        assertEquals("Discounted cash flow valuation with id number " + valuationId + " not found."
                , exception.getMessage());
    }

    @Test
    void validateDcfModelById_DoesExist_DoesNotThrowException() {
        when(dcfModelRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateDcfModelById(1L));
    }

    /* Tests for validateDcfModelForUser */
    @Test
    void validateDcfModelForUser() {
    }

    @Test
    void validateDcfModelForUser_ExistsAndBelongsToUser_NoExceptionThrown() {
        final Long valuationId = 1L;
        final Long userId = 1L;
        var user = new User(
                1L,
                "Andrius",
                "password",
                "myEmail@gmail.com",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        var dcfModel = new DcfModel(
                "Apple",
                "AAPL",
                10.1,
                2.1,
                3.1,
                1.1,
                user
        );

        when(dcfModelRepository.findById(valuationId)).thenReturn(Optional.of(dcfModel));

        assertDoesNotThrow(() -> validator.validateDcfModelForUser(valuationId, userId));
    }

    @Test
    void validateDcfModelForUser_ValuationDoesNotExist_ThrowsException() {
        final Long valuationId = 1L;
        final Long userId = 1L;

        when(dcfModelRepository.findById(valuationId))
                .thenReturn(Optional.empty());

        ValuationDoestExistForSelectedUserException exception = assertThrows(
                ValuationDoestExistForSelectedUserException.class,
                () -> validator.validateDcfModelForUser(valuationId, userId)
        );

        assertEquals("Valuation does not exist for this user", exception.getMessage());
    }

    @Test
    void validateDcfModelForUser_ValuationExistsButBelongsToAnotherUser_ThrowsException() {
        final Long valuationId = 1L;
        final Long userId = 2L;
        var user = new User(
                1L,
                "Andrius",
                "password",
                "myEmail@gmail.com",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        var dcfModel = new DcfModel(
                "Apple",
                "AAPL",
                10.1,
                2.1,
                3.1,
                1.1,
                user
        );

        when(dcfModelRepository.findById(valuationId))
                .thenReturn(Optional.of(dcfModel));

        ValuationDoestExistForSelectedUserException exception = assertThrows(
                ValuationDoestExistForSelectedUserException.class,
                () -> validator.validateDcfModelForUser(valuationId, userId)
        );

        assertEquals("Valuation does not exist for this user", exception.getMessage());
    }
}