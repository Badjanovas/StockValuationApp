package com.example.StockValueApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrahamsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    //Earnings per share
    private Double eps;

    @Column(nullable = false)
    private Double growthRate;

    @Column(nullable = false)
    private Double currentYieldOfBonds;

    @Column(nullable = false)
    private Double intrinsicValue;

    public static final Double BASE_PE = 7.0;
    public static final Double AVERAGE_YIELD_AAA_BONDS = 4.4;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public GrahamsModel(String name, String ticker, Double eps, Double growthRate, Double currentYieldOfBonds, User user) {
        this.name = name;
        this.ticker = ticker;
        this.eps = eps;
        this.growthRate = growthRate;
        this.currentYieldOfBonds = currentYieldOfBonds;
        this.user = user;
    }
}
