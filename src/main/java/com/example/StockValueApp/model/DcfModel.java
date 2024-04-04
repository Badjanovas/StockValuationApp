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
public class DcfModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private Double sumOfFCF;

    @Column(nullable = false)
    private Double cashAndCashEquivalents;

    @Column(nullable = false)
    private Double totalDebt;

    @Column(nullable = false)
    private Double equityValue;

    @Column(nullable = false)
    private Double sharesOutstanding;

    @Column(nullable = false)
    private Double intrinsicValue;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public DcfModel(String ticker, Double sumOfFCF, Double totalDebt, Double equityValue, Double sharesOutstanding, User user) {
        this.ticker = ticker;
        this.sumOfFCF = sumOfFCF;
        this.totalDebt = totalDebt;
        this.equityValue = equityValue;
        this.sharesOutstanding = sharesOutstanding;
        this.user = user;
    }
}
