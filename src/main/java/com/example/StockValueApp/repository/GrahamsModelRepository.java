package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.GrahamsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface GrahamsModelRepository extends JpaRepository<GrahamsModel, Long> {

    List<GrahamsModel> findByUserId(Long id);

    List<GrahamsModel> findByTickerIgnoreCase(String ticker);

    List<GrahamsModel> findByNameIgnoreCase(String name);

    List<GrahamsModel> findByCreationDate(LocalDate creationDate);

}
