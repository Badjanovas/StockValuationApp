package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.DividendDiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DividendDiscountRepository extends JpaRepository<DividendDiscountModel, Long> {

    List<DividendDiscountModel> findByUserId(Long id);

    List<DividendDiscountModel> findByTickerIgnoreCase(String ticker);

    List<DividendDiscountModel> findByCompanyNameIgnoreCase(String companyName);

    List<DividendDiscountModel> findByCreationDate(LocalDate creationDate);

}
