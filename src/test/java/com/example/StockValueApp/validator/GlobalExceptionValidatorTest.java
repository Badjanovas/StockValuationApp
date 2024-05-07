package com.example.StockValueApp.validator;

import com.example.StockValueApp.exception.NotValidIdException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GlobalExceptionValidatorTest {

    @Autowired
    private GlobalExceptionValidator validator;

    /* Tests for validateId */
    @Test
    void validateId_IdNull_ThrowsException(){
        NotValidIdException exception = assertThrows(
                NotValidIdException.class,
                () -> validator.validateId(null)
        );

        assertEquals("Invalid id.", exception.getMessage());
    }

    @Test
    void validateId_IdNegativeValue_ThrowsException(){
        NotValidIdException exception = assertThrows(
                NotValidIdException.class,
                () -> validator.validateId(-1L)
        );

        assertEquals("Invalid id.", exception.getMessage());
    }

    @Test
    void validateId_ValidId_DoesNotThrowException(){
        assertDoesNotThrow(() -> validator.validateId(1L));
    }

}