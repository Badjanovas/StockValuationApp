package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRequestValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRequestValidator validator;

    /* Tests for validateUserRequest */
    @Test
    void validateUserRequest_NullRequest_ThrowsException() {
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(null)
        );
        assertEquals("Request was empty.", exception.getMessage());
    }

    @Test
    void validateUserRequest_NullUserName_ThrowsException() {
        var requestDTO = new UserRequestDTO(null, "Password", "email.gmail.com");
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory user name field is missing.", exception.getMessage());
    }

    @Test
    void validateUserRequest_IsBlankUserName_ThrowsException() {
        var requestDTO = new UserRequestDTO("", "Password", "email.gmail.com");
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory user name field is empty.", exception.getMessage());
    }

    @Test
    void validateUserRequest_NullPassword_ThrowsException() {
        var requestDTO = new UserRequestDTO("Username", null, "email.gmail.com");
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory password field is missing.", exception.getMessage());
    }

    @Test
    void validateUserRequest_IsBlankPassword_ThrowsException() {
        var requestDTO = new UserRequestDTO("Username", "", "email.gmail.com");
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory password field is empty.", exception.getMessage());
    }

    @Test
    void validateUserRequest_NullEmail_ThrowsException() {
        var requestDTO = new UserRequestDTO("Username", "Password", null);
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory email field is missing.", exception.getMessage());
    }

    @Test
    void validateUserRequest_IsBlankEmail_ThrowsException() {
        var requestDTO = new UserRequestDTO("Username", "Password", "");
        MandatoryFieldsMissingException exception = assertThrows(
                MandatoryFieldsMissingException.class,
                () -> validator.validateUserRequest(requestDTO)
        );

        assertEquals("Mandatory email field is empty.", exception.getMessage());
    }

    /* Tests for validateUserList */
    @Test
    void validateUserList_EmptyList_ThrowsException() {
        final List<User> users = new ArrayList<>();

        NoUsersFoundException exception = assertThrows(NoUsersFoundException.class, () -> {
            validator.validateUserList(users);
        });

        assertEquals("No users were found!", exception.getMessage());
    }

    @Test
    void validateUserList_PopulatedList_doesNotThrowException() {
        List<User> users = new ArrayList<>();
        users.add(new User("User1", "Password", "newEmail@gmail.com"));
        users.add(new User("Usuer2", "Password2", "fakeemail@gmail.com"));

        assertDoesNotThrow(() -> validator.validateUserList(users));
    }

    /* Tests for validateUsersById */
    @Test
    void validateUserById_UsersNotFound_ThrowsException() {
        final Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        NoUsersFoundException exception = assertThrows(
                NoUsersFoundException.class,
                () -> validator.validateUserById(userId)
        );

        assertEquals("User with id number " + userId + " not found.", exception.getMessage());
    }

    @Test
    void validateUserById_userExists_doesNotThrowException() {
        final Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateUserById(userId));
    }

    /* Tests for validateUserName */
    @Test
    void validateUserName_userExists_ThrowsException() {
        var requestDTO = new UserRequestDTO("Andrius", "password", "email@email.com");
        when(userRepository.findByUserName(requestDTO.getUserName()))
                .thenReturn(Optional.of(new User("Andrius", "p", "email")));

        UserAlreadyExistException exception = assertThrows(
                UserAlreadyExistException.class,
                () -> validator.validateUserName(requestDTO)
        );

        assertEquals("User with username: " + requestDTO.getUserName() + " already exist. Choose different user name.", exception.getMessage());
    }

    @Test
    void validateUserByUserName_UserDoesNotExist_doesNotThrowException() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("Andrius", "password", "email@email.com");
        when(userRepository.findByUserName(userRequestDTO.getUserName())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validateUserName(userRequestDTO));
    }

    /* Tests for validateEmailFormat */

    /*
     * @ParameterizedTest: This annotation indicates that the method below is not just a regular
     *  test method but a parameterized test method. It means this test will run multiple times,
     *  once for each input provided by the subsequent annotation.
     *  ValueSource(strings = {...}): This is an argument provider for a parameterized test.
     *  It supplies the input data for the test. In this case, it's providing a list of strings.
     *  Each string represents an email address format that is presumably incorrect and will be tested
     *  to verify if the method validateEmailFormat correctly identifies them as such.*/
    @ParameterizedTest
    @ValueSource(strings = {
            "newEmail.yahoo.com",
            "newEmail@gmail@com",
            "newEmail.gmail@com"
    })
    void validateEmailFormat_IncorrectEmailFormat_ThrowsException(String email) {
        IncorrectEmailFormatException exception = assertThrows(
                IncorrectEmailFormatException.class,
                () -> validator.validateEmailFormat(email)
        );

        assertEquals("Invalid email address format: " + email, exception.getMessage());
    }

    @Test
    void validateEmailFormat_CorrectEmailFormat_DoesNotThrowException() {
        String email = "newEmail@gmail.com";

        assertDoesNotThrow(() -> validator.validateEmailFormat(email));
    }

    @Test
    void validateEmailFormat_Null_ThrowsException() {
        IncorrectEmailFormatException exception = assertThrows(
                IncorrectEmailFormatException.class,
                () -> validator.validateEmailFormat(null)
        );

        assertEquals("Invalid email address format: null", exception.getMessage());
    }

    /* Tests for validateEmail*/
    @Test
    void validateEmail_UserWithSameEmailAlreadyExist_ThrowsException() {
        var requestDTO = new UserRequestDTO("Andrius", "password", "email@email.com");

        when(userRepository.findByEmail(requestDTO.getEmail()))
                .thenReturn(Optional.of(new User("Tomas", "tomas123", "email@email.com")));

        EmailAlreadyExistException exception = assertThrows(
                EmailAlreadyExistException.class,
                () -> validator.validateEmail(requestDTO)
        );

        assertEquals("User with email address " + requestDTO.getEmail() + " already exist.", exception.getMessage());
    }

    @Test
    void validateEmail_UserWithProvidedEmailDoesNotExist_DoesNotThrowException() {
        var requestDTO = new UserRequestDTO("Andrius", "password", "email@email.com");

        when(userRepository.findByEmail(requestDTO.getEmail()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validateEmail(requestDTO));
    }
}