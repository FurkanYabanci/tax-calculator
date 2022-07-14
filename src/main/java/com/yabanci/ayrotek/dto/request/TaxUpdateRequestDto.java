package com.yabanci.ayrotek.dto.request;

import com.yabanci.ayrotek.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class TaxUpdateRequestDto {

    private Long id;
    private ProductType productType;
    private BigDecimal rate;

}
