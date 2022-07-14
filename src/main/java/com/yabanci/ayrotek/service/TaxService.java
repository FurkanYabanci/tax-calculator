package com.yabanci.ayrotek.service;

import com.yabanci.ayrotek.converter.TaxMapper;
import com.yabanci.ayrotek.dto.TaxDto;
import com.yabanci.ayrotek.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotek.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotek.enums.TaxErrorMessage;
import com.yabanci.ayrotek.exception.ItemNotFoundException;
import com.yabanci.ayrotek.model.Product;
import com.yabanci.ayrotek.model.ProductType;
import com.yabanci.ayrotek.model.Tax;
import com.yabanci.ayrotek.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private ProductService productService;

    @Autowired
    public void setProductService(@Lazy ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal calculateTax(BigDecimal price, ProductType productType){
        BigDecimal taxIncludedPrice = price.
                multiply(findByProductType(productType).getRate()).
                add(price);
        return taxIncludedPrice;
    }

    public List<TaxDto> findAll(){
        List<Tax> taxes = taxRepository.findAll();
        List<TaxDto> taxDtoList = TaxMapper.INSTANCE.convertToTaxDtoList(taxes);
        return taxDtoList;
    }
    public TaxDto findByProductType(ProductType productType){
        Tax tax = taxRepository.findByProductType(productType);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }
    public TaxDto save(TaxSaveRequestDto taxSaveRequestDto){
        Tax tax = TaxMapper.INSTANCE.convertToTax(taxSaveRequestDto);
        tax = taxRepository.save(tax);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }

    @Transactional
    public TaxDto update(TaxUpdateRequestDto taxUpdateRequestDto){
        Tax tax = findById(taxUpdateRequestDto.getId());
        List<Product> products = productService.findAllByTaxId(taxUpdateRequestDto.getId());
        for (Product product:products)
        {
            productService.updateVatRateAndTaxIncludedPrice(taxUpdateRequestDto,product);
        }
        tax.setProductType(taxUpdateRequestDto.getProductType());
        tax.setRate(taxUpdateRequestDto.getRate());
        tax = taxRepository.save(tax);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }

    public void delete(long id){
        Tax tax = findById(id);
        taxRepository.delete(tax);
    }

    protected Tax findById(long id){
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(TaxErrorMessage.TAX_NOT_FOUND));
        return tax;
    }
}
