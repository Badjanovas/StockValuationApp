package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.dto.UserResponseDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.mappingService.UserMappingService;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import com.example.StockValueApp.validator.UserRequestValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRequestValidator userRequestValidator;
    @Mock
    private UserMappingService userMappingService;
    @Mock
    private GlobalExceptionValidator globalExceptionValidator;

    @InjectMocks
    private UserService userService;
    private UserRequestDTO userRequestDTO;
    private User user;
    private UserResponseDTO userResponseDTO;

    // Tests for getAllUsers
    @Test
    void getAllUsersReturnsEmptyList() throws NoUsersFoundException {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.getAllUsers();

        assertTrue(users.isEmpty(), "Expected an empty list of users.");
        Mockito.verify(userRequestValidator).validateUserList(users);
    }

    @Test
    void getAllUsersReturnsPopulatedList() throws NoUsersFoundException {
        User user1 = new User("User1", "password111", "email@gmail");
        User user2 = new User("User2", "password2","emaill@gmail");
        User user3 = new User("User3", "password12","emmaill@gmail");
        User user4 = new User("User4", "password4444","eemaill@gmail");

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3, user4));

        List<User> users = userService.getAllUsers();
        assertEquals(4, users.size());
        assertEquals("User1", users.get(0).getUserName());
        assertEquals("User4", users.get(3).getUserName());
        Mockito.verify(userRequestValidator).validateUserList(users);
    }

    @Test
    void getAllUsersThrowsNoUsersFoundException() throws NoUsersFoundException {
        Mockito.doThrow(new NoUsersFoundException("No users found."))
                .when(userRequestValidator).validateUserList(new ArrayList<>());

        Exception exception = assertThrows(NoUsersFoundException.class, () -> userService.getAllUsers());
        assertNotNull(exception);
    }

    // Tests for addUser
    @Test
    void addUserShouldAddUserWhenValidRequest() throws Exception {
        userRequestDTO = new UserRequestDTO("username", "password", "email@example.com");
        user = new User("username", "password", "email@example.com");
        userResponseDTO = new UserResponseDTO(1L, "username", "email@example.com", null);

        Mockito.when(userMappingService.mapToEntity(Mockito.any(UserRequestDTO.class))).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userMappingService.mapToResponse(Mockito.anyList())).thenReturn(Collections.singletonList(userResponseDTO));

        List<UserResponseDTO> result = userService.addUser(userRequestDTO);

        Mockito.verify(userRequestValidator).validateUserName(userRequestDTO);
        Mockito.verify(userRequestValidator).validateUserRequest(userRequestDTO);
        Mockito.verify(userRequestValidator).validateEmailFormat(userRequestDTO.getEmail());
        Mockito.verify(userRequestValidator).validateEmail(userRequestDTO);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userMappingService).mapToResponse(Mockito.anyList());

        assertEquals(1, result.size());
        assertEquals("username", result.get(0).getUserName());
    }
    @Test
    void addUserShouldThrowUserAlreadyExistWhenUserNameExists() throws UserAlreadyExist {
        UserRequestDTO userRequestDTO = new UserRequestDTO("existingUsername", "password", "email@example.com");

        Mockito.doThrow(new UserAlreadyExist("User with username: existingUsername already exists. Choose a different username."))
                .when(userRequestValidator).validateUserName(Mockito.any(UserRequestDTO.class));

        Exception exception = assertThrows(UserAlreadyExist.class, () -> {
            userService.addUser(userRequestDTO);
        });

        String expectedMessage = "User with username: existingUsername already exists. Choose a different username.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void addUserShouldThrowIncorrectEmailFormatExceptionWhenEmailFormatIsInvalid() throws IncorrectEmailFormatException {
        UserRequestDTO userRequestDTO = new UserRequestDTO("newUsername", "password", "invalidEmail");

        Mockito.doThrow(new IncorrectEmailFormatException("Invalid email address format: invalidEmail"))
                .when(userRequestValidator).validateEmailFormat(userRequestDTO.getEmail());

        Exception exception = assertThrows(IncorrectEmailFormatException.class, () -> {
            userService.addUser(userRequestDTO);
        });

        assertEquals("Invalid email address format: invalidEmail", exception.getMessage());
    }

    @Test
    void addUserShouldThrowEmailAlreadyExistWhenEmailAlreadyExists() throws EmailAlreadyExist {
        UserRequestDTO userRequestDTO = new UserRequestDTO("newUsername", "password", "existingEmail@example.com");

        Mockito.doThrow(new EmailAlreadyExist("User with email address existingEmail@example.com already exists."))
                .when(userRequestValidator).validateEmail(userRequestDTO);

        Exception exception = assertThrows(EmailAlreadyExist.class, () -> {
            userService.addUser(userRequestDTO);
        });

        assertEquals("User with email address existingEmail@example.com already exists.", exception.getMessage());
    }

    @Test
    void addUserShouldThrowMandatoryFieldsMissingExceptionWhenRequiredFieldsAreMissing() throws MandatoryFieldsMissingException {
        UserRequestDTO userRequestDTO = new UserRequestDTO("", "", "");

        Mockito.doThrow(new MandatoryFieldsMissingException("Mandatory user name field is missing."))
                .when(userRequestValidator).validateUserRequest(Mockito.any(UserRequestDTO.class));

        Exception exception = assertThrows(MandatoryFieldsMissingException.class, () -> {
            userService.addUser(userRequestDTO);
        });

        assertTrue(exception.getMessage().contains("Mandatory user name field is missing."));
    }
}