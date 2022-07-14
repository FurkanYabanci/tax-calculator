package com.yabanci.ayrotek.service;

import com.yabanci.ayrotek.converter.ProductMapper;
import com.yabanci.ayrotek.dto.ProductDto;
import com.yabanci.ayrotek.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotek.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotek.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotek.enums.ProductErrorMessage;
import com.yabanci.ayrotek.exception.ItemNotFoundException;
import com.yabanci.ayrotek.model.Product;
import com.yabanci.ayrotek.model.Tax;
import com.yabanci.ayrotek.model.User;
import com.yabanci.ayrotek.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TaxService taxService;

    private final UserService userService;

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return productDtoList;
    }

    public ProductDto save(ProductSaveRequestDto productSaveRequestDto){
        Product product = ProductMapper.INSTANCE.convertToProduct(productSaveRequestDto);
        Tax tax = taxService.findById(productSaveRequestDto.getTaxId());
        User user = userService.findById(productSaveRequestDto.getUserId());
        BigDecimal taxIncludedPrice = product.getTaxFreePrice().multiply(tax.getRate())
                .add(product.getTaxFreePrice());
        product.setTaxIncludedPrice(taxIncludedPrice);
        product.setTax(tax);
        product.setUser(user);
        product.setProductType(tax.getProductType());
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public ProductDto update(ProductUpdateRequestDto productUpdateRequestDto){
        Product product = findById(productUpdateRequestDto.getId());
        product.setName(productUpdateRequestDto.getName());
        product.setTaxFreePrice(productUpdateRequestDto.getTaxFreePrice());
        User user = userService.findById(productUpdateRequestDto.getUserId());
        Tax tax = taxService.findById(productUpdateRequestDto.getTaxId());
        product.setTax(tax);
        BigDecimal taxIncludedPrice = productUpdateRequestDto.getTaxFreePrice().multiply(tax.getRate())
                .add(product.getTaxFreePrice());
        product.setUser(user);
        product.setTaxIncludedPrice(taxIncludedPrice);
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public void delete(long id){
        Product product = findById(id);
        productRepository.delete(product);
    }

    protected Product findById(long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ProductErrorMessage.PRODUCT_NOT_FOUND));
        return product;
    }

    protected List<Product> findAllByTaxId(long id){
        List<Product> products = productRepository.findAllByTax_Id(id);
        return products;
    }

    @Transactional
    protected Product updateVatRateAndTaxIncludedPrice(TaxUpdateRequestDto taxUpdateRequestDto, Product product){
        BigDecimal taxIncludedPrice = product.getTaxFreePrice().multiply(taxUpdateRequestDto.getRate())
                .add(product.getTaxFreePrice());
        product.setTaxIncludedPrice(taxIncludedPrice);
        Tax tax = taxService.findById(taxUpdateRequestDto.getId());
        product.setTax(tax);
        product = productRepository.save(product);
        return product;
    }
}