package com.yabanci.ayrotek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(precision = 19, scale = 2, nullable = false)
    @Min(value = 1)
    private BigDecimal taxFreePrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal taxIncludedPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Tax tax;
}