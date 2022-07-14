package com.yabanci.ayrotek.service;

import com.yabanci.ayrotek.dto.ProductDto;
import com.yabanci.ayrotek.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotek.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotek.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotek.exception.ItemNotFoundException;
import com.yabanci.ayrotek.model.Product;
import com.yabanci.ayrotek.model.Tax;
import com.yabanci.ayrotek.model.User;
import com.yabanci.ayrotek.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;
    @Mock
    private TaxService taxService;

    @Spy
    @InjectMocks
    private ProductService productService;

    @Test
    void shouldFindAll() {
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();

        assertEquals(0, result.size());
    }


    @Test
    void shouldSave() {
        ProductSaveRequestDto productSaveRequestDto = mock(ProductSaveRequestDto.class);
        Tax tax = mock(Tax.class);
        Product product = mock(Product.class);
        User user = mock(User.class);

        when(taxService.findById(anyLong())).thenReturn(tax);
        when(productSaveRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(userService.findById(anyLong())).thenReturn(user);
        when(tax.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.save(productSaveRequestDto);

        assertEquals(result.getName(),productSaveRequestDto.getName());

        verify(taxService).findById(anyLong());
    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> productService.save(null));
    }

    @Test
    void shouldUpdate() {
        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
        Tax tax = mock(Tax.class);
        Product product = mock(Product.class);
        User user = mock(User.class);

        doReturn(product).when(productService).findById(anyLong());
        when(taxService.findById(anyLong())).thenReturn(tax);
        when(userService.findById(anyLong())).thenReturn(user);
        when(productUpdateRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(product.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(tax.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(productRepository.save(product)).thenReturn(product);

        ProductDto result = productService.update(productUpdateRequestDto);

        assertEquals(result.getId(),productUpdateRequestDto.getId());

        verify(taxService).findById(anyLong());
    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist() {

        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.update(productUpdateRequestDto));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {

        Product product = mock(Product.class);

        doReturn(product).when(productService).findById(anyLong());

        productService.delete(anyLong());

        verify(productService).findById(anyLong());
        verify(productRepository).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.delete(anyLong()));

        verify(productRepository).findById(anyLong());
    }


    @Test
    void shouldFindById() {
        Product product = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.findById(anyLong());

        assertEquals(result.getId(), product.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> productService.findById(anyLong()));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldFindAllByTaxId() {

        Tax tax = mock(Tax.class);
        when(tax.getId()).thenReturn(Long.valueOf(1));

        Product product = mock(Product.class);
        when(product.getTax()).thenReturn(tax);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAllByTax_Id(1)).thenReturn(productList);

        List<Product> result = productService.findAllByTaxId(1);

        assertEquals(1,result.get(0).getTax().getId());
    }

    @Test
    void updateVatRateAndTaxIncludedPrice() {
        TaxUpdateRequestDto taxUpdateRequestDto = mock(TaxUpdateRequestDto.class);
        Product product = mock(Product.class);
        Tax tax = mock(Tax.class);

        when(product.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(taxUpdateRequestDto.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(taxService.findById(anyLong())).thenReturn(tax);
        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.updateVatRateAndTaxIncludedPrice(taxUpdateRequestDto,product);
        assertEquals(taxUpdateRequestDto.getId(), result.getId());
    }
}