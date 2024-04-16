package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.DividendDiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendDiscountRepository extends JpaRepository<DividendDiscountModel, Long> {
}
