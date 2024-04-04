package com.example.StockValueApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DividendDiscountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private Double valueOfNexYearsDiv;

    @Column(nullable = false)
    //Weighted average cost of capital
    private Double wacc;

    @Column(nullable = false)
    private Double growthRate;

    @Column(nullable = false)
    private Double intrinsicValue;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public DividendDiscountModel(String name, String ticker, Double valueOfNexYearsDiv, Double wacc, Double growthRate, User user) {
        this.name = name;
        this.ticker = ticker;
        this.valueOfNexYearsDiv = valueOfNexYearsDiv;
        this.wacc = wacc;
        this.growthRate = growthRate;
        this.user = user;
    }
}
