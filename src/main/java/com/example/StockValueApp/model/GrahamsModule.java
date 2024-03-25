package com.example.StockValueApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrahamsModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String stockTicker;
    @Column(nullable = false)
    private Double eps;
    @Column(nullable = false)
    private Double growthRate;
    @Column(nullable = false)
    private Double currentYieldOfBonds;
    @Column(nullable = false)
    private Double intrinsicValue;

    public static final Double BASE_PE = 7.0;
    public static final Double AVERAGE_YIELD_AAA_BONDS = 4.4;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public GrahamsModule(String stockTicker, Double eps, Double growthRate, Double currentYieldOfBonds) {
        this.stockTicker = stockTicker;
        this.eps = eps;
        this.growthRate = growthRate;
        this.currentYieldOfBonds = currentYieldOfBonds;
    }

    public GrahamsModule(String stockTicker, Double eps, Double growthRate, Double currentYieldOfBonds, User user) {
        this.stockTicker = stockTicker;
        this.eps = eps;
        this.growthRate = growthRate;
        this.currentYieldOfBonds = currentYieldOfBonds;
        this.user = user;
    }
}
