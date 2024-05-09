package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.model.AuthenticationRequest;
import com.example.StockValueApp.model.AuthenticationResponse;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.mappingService.UserMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final UserMappingService mappingService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


}
