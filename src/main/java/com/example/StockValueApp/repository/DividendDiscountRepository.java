package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.DividendDiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DividendDiscountRepository extends JpaRepository<DividendDiscountModel, Long> {

    List<DividendDiscountModel> findByUserId(Long id);

    List<DividendDiscountModel> findByTickerIgnoreCase(String ticker);

    List<DividendDiscountModel> findByCompanyNameIgnoreCase(String companyName);

    List<DividendDiscountModel> findByCreationDate(LocalDate creationDate);

}
