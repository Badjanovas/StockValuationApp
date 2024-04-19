package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.model.GrahamsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DcfModelRepository extends JpaRepository<DcfModel, Long> {

    List<DcfModel> findByUserId(Long id);

    List<DcfModel> findByTickerIgnoreCase(String ticker);

    List<DcfModel> findByCompanyNameIgnoreCase(String companyName);

    List<DcfModel> findByCreationDate(LocalDate creationDate);
}
