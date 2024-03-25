package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.UserResponseDTO;
import com.example.StockValueApp.exception.NoUsersFoundException;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepo;
import com.example.StockValueApp.service.mappingService.UserMappingService;
import com.example.StockValueApp.validator.UserRequestValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Data
public class UserService {

    private final UserRepo repository;
    private final UserMappingService mappingService;
    private final UserRequestValidator userRequestValidator;

    public List<User> getAllUsers() throws NoUsersFoundException {
        log.info("Looking for users in DB...");
        List<User> users = repository.findAll();
        userRequestValidator.validateUserList(users);
        return users;
    }

}
