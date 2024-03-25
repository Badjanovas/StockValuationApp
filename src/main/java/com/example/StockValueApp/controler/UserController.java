package com.example.StockValueApp.controler;

import com.example.StockValueApp.exception.NoUsersFoundException;
import com.example.StockValueApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
