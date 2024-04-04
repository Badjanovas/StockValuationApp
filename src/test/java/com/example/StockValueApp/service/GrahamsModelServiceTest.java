package com.example.StockValueApp.service;

import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.repository.GrahamsValuationRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class GrahamsModelServiceTest {
    @Mock
    private GrahamsValuationRepo repository;
    @InjectMocks
    private GrahamsModelService service;



}