package com.example.StockValueApp.repository;

import com.example.StockValueApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
