package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.dto.UserResponseDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.mappingService.UserMappingService;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import com.example.StockValueApp.validator.UserRequestValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Data
public class UserService {

    private final UserRepository userRepository;
    private final UserMappingService userMappingService;
    private final UserRequestValidator userRequestValidator;
    private final GlobalExceptionValidator globalExceptionValidator;
    private final EmailSendingService emailSendingService;

    public List<User> getAllUsers() throws NoUsersFoundException {
        log.info("Looking for users in DB.");
        final List<User> users = userRepository.findAll();
        userRequestValidator.validateUserList(users);
        log.info(users.size() + " users were found in DB.");
        return users;
    }

    // returnus susitvarkyti. cia turbut voido uztenka
    public List<UserResponseDTO> addUser(final UserRequestDTO userRequestDTO) throws MandatoryFieldsMissingException, UserAlreadyExistException, IncorrectEmailFormatException, EmailAlreadyExistException, NotValidIdException {
        userRequestValidator.validateUserName(userRequestDTO);
        userRequestValidator.validateUserRequest(userRequestDTO);
        userRequestValidator.validateEmailFormat(userRequestDTO.getEmail());
        userRequestValidator.validateEmail(userRequestDTO);

        final User user = userMappingService.mapToEntity(userRequestDTO);
        userRepository.save(user);
        globalExceptionValidator.validateId(user.getId());
        emailSendingService.sendEmail(userRequestDTO.getEmail(), userRequestDTO);
        log.info("New user " + user.getUserName() + " was created and saved successfully.");
        return userMappingService.mapToResponse(userRepository.findAll());
    }

    //manau ir cia voido uzteks
    public List<UserResponseDTO> deleteUserById(final Long id) throws NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(id);
        userRequestValidator.validateUserById(id);
        userRepository.deleteById(id);
        log.info("User with id number " + id + " was deleted from DB successfully.");

        return userMappingService.mapToResponse(userRepository.findAll());
    }

    // Noriu padaryti kad kiekvienas useris turetu galimybe pasikeisti userName, password ir email.
    // apsvarstyti isskirstyma i atskirus metodus?
    public User updateUserByUserName(final String userName, final UserRequestDTO user) throws NoUsersFoundException, UserAlreadyExistException, EmailAlreadyExistException, IncorrectEmailFormatException, MandatoryFieldsMissingException {
        userRequestValidator.validateUserRequest(user);
        User userToUpdate = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NoUsersFoundException("User with username: " + userName + " was not found."));

        userRequestValidator.validateUserName(user);
        userRequestValidator.validateEmail(user);
        userRequestValidator.validateEmailFormat(user.getEmail());

        final boolean isUpdated = updateUserIfChanged(user, userToUpdate);
        if (isUpdated) {
            userRepository.save(userToUpdate);
            log.info("User " + userToUpdate.getUserName() + " was updated successfully.");
        }

        return userToUpdate;
    }

    private boolean updateUserIfChanged(final UserRequestDTO user, final User userToUpdate) {
        boolean isUpdated = false;

        if (!Objects.equals(userToUpdate.getUserName(), user.getUserName())) {
            userToUpdate.setUserName(user.getUserName());
            isUpdated = true;
        }
        if (!Objects.equals(userToUpdate.getPassword(), user.getPassword())) {
            userToUpdate.setPassword(user.getPassword());
            isUpdated = true;
        }
        if (!Objects.equals(userToUpdate.getEmail(), user.getEmail())) {
            userToUpdate.setEmail(user.getEmail());
            isUpdated = true;
        }
        return isUpdated;
    }

}
