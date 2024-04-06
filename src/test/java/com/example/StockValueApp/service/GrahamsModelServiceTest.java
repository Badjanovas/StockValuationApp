package com.example.StockValueApp.service;

import com.example.StockValueApp.repository.GrahamsModelRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GrahamsModelServiceTest {
    @Mock
    private GrahamsModelRepository repository;
    @InjectMocks
    private GrahamsModelService service;



}