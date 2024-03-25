package com.example.StockValueApp.service;

import com.example.StockValueApp.exception.NoUsersFoundException;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepo;
import com.example.StockValueApp.validator.UserRequestValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private UserRequestValidator userRequestValidator;
    @InjectMocks
    private UserService userService;

    // Tests for getAllUsers
    @Test
    void getAllUsersReturnsEmptyList() throws NoUsersFoundException {
        Mockito.when(userRepo.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.getAllUsers();

        assertTrue(users.isEmpty(), "Expected an empty list of users.");
    }

    @Test
    void getAllUsersReturnsPopulatedList


}