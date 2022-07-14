package com.yabanci.ayrotek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(nullable = false)
    @Min(value = 0)
    private BigDecimal rate;

    @OneToMany(mappedBy = "tax",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products;
}
