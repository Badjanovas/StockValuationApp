package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoUsersFoundException;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRequestValidator {

    private final UserRepo repository;

    public void validateUserRequest(final UserRequestDTO userRequestDTO) throws MandatoryFieldsMissingException {
        if (userRequestDTO == null){
            log.error("Request was empty.");
            throw new MandatoryFieldsMissingException("Request was empty.");
        } else if (userRequestDTO.getUserName() == null){
            log.error("Mandatory user name field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory user name field is missing.");
        } else if (userRequestDTO.getUserName().isBlank()) {
            log.error("Mandatory user name field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory user name field is empty.");
        } else if (userRequestDTO.getPassword() == null) {
            log.error("Mandatory password field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory password field is missing.");
        } else if (userRequestDTO.getPassword().isBlank()) {
            log.error("Mandatory password field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory password field is empty.");
        }
    }

    public void validateUserList(final List<User> users) throws NoUsersFoundException {
        if (users.isEmpty()) {
            log.error("No users were found in the DB!");
            throw new NoUsersFoundException("No users were found!");
        }
    }

}
