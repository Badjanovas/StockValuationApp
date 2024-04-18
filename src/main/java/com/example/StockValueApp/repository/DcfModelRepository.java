package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.model.GrahamsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DcfModelRepository extends JpaRepository<DcfModel, Long> {

    List<DcfModel> findByTickerIgnoreCase(String ticker);

    List<DcfModel> findByCompanyNameIgnoreCase(String companyName);

    List<DcfModel> findByCreationDate(LocalDate creationDate);
}
