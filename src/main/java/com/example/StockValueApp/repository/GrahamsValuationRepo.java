package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.GrahamsModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrahamsValuationRepo extends JpaRepository<GrahamsModule, Long> {
}
