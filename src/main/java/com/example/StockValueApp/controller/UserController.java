package com.example.StockValueApp.controller;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.AuthenticationRequest;
import com.example.StockValueApp.model.AuthenticationResponse;
import com.example.StockValueApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() throws NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody final UserRequestDTO userRequestDTO) throws MandatoryFieldsMissingException, UserAlreadyExistException, IncorrectEmailFormatException, EmailAlreadyExistException, NotValidIdException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(userRequestDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long userId) throws NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable final String userName, @RequestBody final UserRequestDTO user) throws NoUsersFoundException, UserAlreadyExistException, EmailAlreadyExistException, IncorrectEmailFormatException, MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserByUserName(userName, user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws MandatoryFieldsMissingException {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequestDTO request) throws UserAlreadyExistException, IncorrectEmailFormatException, NotValidIdException, MandatoryFieldsMissingException, EmailAlreadyExistException {
        return ResponseEntity.ok(userService.register(request));
    }

}
