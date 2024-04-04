package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.GrahamsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrahamsValuationRepo extends JpaRepository<GrahamsModel, Long> {
}
