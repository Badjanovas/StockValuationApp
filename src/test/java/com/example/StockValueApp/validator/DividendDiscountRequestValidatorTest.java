package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.exception.IncorrectCompaniesExpectedGrowthException;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoDividendDiscountModelFoundException;
import com.example.StockValueApp.exception.ValuationDoestExistForSelectedUserException;
import com.example.StockValueApp.model.DividendDiscountModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.DividendDiscountRepository;
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
class DividendDiscountRequestValidatorTest {

    @Mock
    private DividendDiscountRepository dividendDiscountRepository;

    @InjectMocks
    private DividendDiscountRequestValidator validator;

    /* Tests for validateDividendDiscountRequest */
    @Test
    void validateDividendDiscountRequest_NullRequest_ThrowsException() {
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest(null)
        );
        assertEquals("Request was empty.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_CompanyNameNull_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                null,
                "ticker",
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory company name field is missing.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_CompanyNameIsBlank_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "",
                "ticker",
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory company name field is empty.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_CompanyTickerNull_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "company",
                null,
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );

        assertEquals("Mandatory company ticker field is missing.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_CompanyTickerIsBlank_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "company",
                "",
                1.1,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory company ticker field is empty.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_CurrentYearsDivIsMissing_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "company",
                "ticker",
                null,
                1.1,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory currentYearsDiv field is missing.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_WaccFieldIsMissing_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "company",
                "ticker",
                1.1,
                null,
                1.1
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory wacc field is missing.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountRequest_ExpectedGrowthRateIsMissing_ThrowsException() {
        var requestDTO = new DividendDiscountRequestDTO(
                "company",
                "ticker",
                1.1,
                1.1,
                null
        );

        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateDividendDiscountRequest
                        (requestDTO)
        );
        assertEquals("Mandatory expectedGrowthRate field is missing.", exception.getMessage());

    }

    /* Tests for validateDividendDiscountList */
    @Test
    void validateDividendDiscountList_EmptyList_ThrowsException() {
        List<DividendDiscountModel> valuations = new ArrayList<>();

        NoDividendDiscountModelFoundException exception = assertThrows(
                NoDividendDiscountModelFoundException.class,
                () -> validator.validateDividendDiscountList(valuations)
        );

        assertEquals("No dividend discount valuations found.", exception.getMessage());
    }

    @Test
    void validateDividendDiscountList_PopulatedList_DoesNotThrowException() {
        List<DividendDiscountModel> valuations = new ArrayList<>();
        valuations.add(new DividendDiscountModel());
        valuations.add(new DividendDiscountModel());
        valuations.add(new DividendDiscountModel());

        assertDoesNotThrow(() -> validator.validateDividendDiscountList(valuations));
    }

    /* Tests for validateDividendDiscountById */
    @Test
    void validateDividendDiscountById_DoesNotExist_ThrowsException() {
        Long valuationId = 1L;
        when(dividendDiscountRepository.existsById(valuationId)).thenReturn(false);

        NoDividendDiscountModelFoundException exception = assertThrows(
                NoDividendDiscountModelFoundException.class,
                () -> validator.validateDividendDiscountById(valuationId)
        );

        assertEquals("Dividend discount valuation with id number "
                + valuationId
                + " not found.", exception.getMessage()
        );
    }

    @Test
    void validateDividendDiscountById_DoesExist_DoesNotThrowException() {
        Long valuationId = 1L;
        when(dividendDiscountRepository.existsById(valuationId)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateDividendDiscountById(valuationId));
    }

    /* Tests for validateExpectedGrowthRateInput */
    @Test
    void validateExpectedGrowthRateInput_ExpectedGrowthHigherThenWacc_ThrowsException() {
        final double wacc = 9.0;
        final double expectedGrowth = 9.5;

        IncorrectCompaniesExpectedGrowthException exception = assertThrows(
                IncorrectCompaniesExpectedGrowthException.class,
                () -> validator.validateExpectedGrowthRateInput(wacc, expectedGrowth)
        );

        assertEquals("Dividend discount model isn't suitable" +
                        " to calculate intrinsic value if expected growth" +
                        " rate is higher or equal to the weighted average cost of capital.",
                exception.getMessage()
        );
    }

    @Test
    void validateExpectedGrowthRateInput_ExpectedGrowthEqualToWacc_ThrowsException() {
        final double wacc = 9.0;
        final double expectedGrowth = 9.0;

        IncorrectCompaniesExpectedGrowthException exception = assertThrows(
                IncorrectCompaniesExpectedGrowthException.class,
                () -> validator.validateExpectedGrowthRateInput(wacc, expectedGrowth)
        );

        assertEquals("Dividend discount model isn't suitable to calculate" +
                        " intrinsic value if expected growth rate is higher or" +
                        " equal to the weighted average cost of capital.",
                exception.getMessage());
    }

    @Test
    void validateExpectedGrowthRateInput_ExpectedGrowthLowerThanWacc_DoesNotThrowException() {
        final double wacc = 9.0;
        final double expectedGrowth = 7.5;

        assertDoesNotThrow(() -> validator.validateExpectedGrowthRateInput(wacc, expectedGrowth));
    }

    /* Tests for validateDividendDiscountModelForUser */
    @Test
    void validateDividendDiscountModelForUser_ExistsAndBelongsToUser_NoExceptionThrown() {
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

        var dividendDiscountModel = new DividendDiscountModel(
                1L,
                "Apple",
                "AAPL",
                1.1,
                1.1,
                1.1,
                1.5,
                5.5,
                null,
                user
        );

        when(dividendDiscountRepository.findById(valuationId))
                .thenReturn(Optional.of(dividendDiscountModel));

        assertDoesNotThrow(() -> validator.validateDividendDiscountModelForUser(valuationId, userId));
    }

    @Test
    void validateDividendDiscountModelForUser_ValuationDoesNotExist_ThrowsException() {
        final Long valuationId = 1L;
        final Long userId = 1L;
        when(dividendDiscountRepository.findById(valuationId))
                .thenReturn(Optional.empty());

        ValuationDoestExistForSelectedUserException exception = assertThrows(
                ValuationDoestExistForSelectedUserException.class,
                () -> validator.validateDividendDiscountModelForUser(valuationId, userId)
        );

        assertEquals("Valuation does not exist for this user", exception.getMessage());
    }

    @Test
    void validateDividendDiscountModelForUser_ValuationExistsButBelongsToAnotherUser_ThrowsException() {
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

        var dividendDiscountModel = new DividendDiscountModel(
                1L,
                "Apple",
                "AAPL",
                1.1,
                1.1,
                1.1,
                1.5,
                5.5,
                null,
                user
        );

        when(dividendDiscountRepository.findById(valuationId))
                .thenReturn(Optional.of(dividendDiscountModel));

        ValuationDoestExistForSelectedUserException exception = assertThrows(
                ValuationDoestExistForSelectedUserException.class,
                () -> validator.validateDividendDiscountModelForUser(valuationId, userId)
        );

        assertEquals("Valuation does not exist for this user", exception.getMessage());
    }
}