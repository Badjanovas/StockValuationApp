package com.example.StockValueApp.controler;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.exception.*;
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
    public ResponseEntity<?> addUser(@RequestBody final UserRequestDTO userRequestDTO) throws MandatoryFieldsMissingException, UserAlreadyExist, IncorrectEmailFormatException, EmailAlreadyExist, NotValidIdException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRequestDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long userId) throws NotValidIdException, NoUsersFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable final String userName, @RequestBody final UserRequestDTO user) throws NoUsersFoundException, UserAlreadyExist, EmailAlreadyExist, IncorrectEmailFormatException, MandatoryFieldsMissingException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserByUserName(userName, user));
    }

}
