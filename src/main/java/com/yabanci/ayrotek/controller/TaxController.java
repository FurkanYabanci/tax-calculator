package com.yabanci.ayrotek.controller;

import com.yabanci.ayrotek.dto.GeneralResponseDto;
import com.yabanci.ayrotek.dto.TaxDto;
import com.yabanci.ayrotek.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotek.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotek.model.ProductType;
import com.yabanci.ayrotek.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/taxes")
public class TaxController {

    private final TaxService taxService;

    @GetMapping("/{price}/{productType}")
    public BigDecimal calculateTax(@PathVariable BigDecimal price, @PathVariable ProductType productType){
        BigDecimal taxIncludedPrice = taxService.calculateTax(price,productType);
        return taxIncludedPrice;
    }

    @GetMapping
    public ResponseEntity findAll(){
        List<TaxDto> taxDtoList = taxService.findAll();
        return ResponseEntity.ok(GeneralResponseDto.of(taxDtoList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody TaxSaveRequestDto taxSaveRequestDto) {
        TaxDto taxDto = taxService.save(taxSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(taxDto));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody TaxUpdateRequestDto taxUpdateRequestDto){
        TaxDto taxDto = taxService.update(taxUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(taxDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        taxService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("Tax Deleted"));
    }
}
