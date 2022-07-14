package com.yabanci.ayrotek.service;

import com.yabanci.ayrotek.dto.TaxDto;
import com.yabanci.ayrotek.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotek.exception.ItemNotFoundException;
import com.yabanci.ayrotek.model.Tax;
import com.yabanci.ayrotek.repository.TaxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private ProductService productService;

    @Spy
    @InjectMocks
    private TaxService taxService;

    @Test
    void shouldFindAll() {
        Tax tax = mock(Tax.class);
        List<Tax> taxList = new ArrayList<>();
        taxList.add(tax);

        when(taxRepository.findAll()).thenReturn(taxList);

        List<TaxDto> result = taxService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenTaxListIsEmpty() {
        List<Tax> taxList = new ArrayList<>();

        when(taxRepository.findAll()).thenReturn(taxList);

        List<TaxDto> result = taxService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findByProductType() {
        Tax tax = mock(Tax.class);

        when(taxRepository.findByProductType(any())).thenReturn(tax);

        TaxDto result = taxService.findByProductType(any());
        assertEquals(tax.getId(),result.getId());
    }

    @Test
    void shouldSave() {
        TaxSaveRequestDto taxSaveRequestDto = mock(TaxSaveRequestDto.class);
        Tax tax = mock(Tax.class);

        when(taxRepository.save(any())).thenReturn(tax);

        TaxDto result = taxService.save(taxSaveRequestDto);

        assertEquals(result.getProductType(), taxSaveRequestDto.getProductType());
    }

    @Test
    void update() {
      /*  TaxUpdateRequestDto taxUpdateRequestDto =mock(TaxUpdateRequestDto.class);
        Tax tax = mock(Tax.class);
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(taxUpdateRequestDto.getId()).thenReturn(Long.valueOf(1));
        doReturn(tax).when(taxService).findById(anyLong());
        when(productService.findAllByTaxId(1)).thenReturn(productList);
        // when(productService.updateVatRateAndTaxIncludedPrice(vatUpdateRequestDto,product)).thenReturn(product);
        TaxDto result = taxService.update(taxUpdateRequestDto);
        assertEquals(result.getProductType(),taxUpdateRequestDto.getProductType());*/
    }

    @Test
    void shouldDelete() {
        Tax tax = mock(Tax.class);

        doReturn(tax).when(taxService).findById(anyLong());

        taxService.delete(anyLong());

        verify(taxService).findById(anyLong());
        verify(taxRepository).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(taxRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> taxService.delete(anyLong()));

        verify(taxRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        Tax tax = mock(Tax.class);

        when(taxRepository.findById(anyLong())).thenReturn(Optional.ofNullable(tax));

        Tax result = taxService.findById(anyLong());
        assertEquals(tax.getId(),result.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(taxRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> taxService.findById(anyLong()));
        verify(taxRepository).findById(anyLong());
    }
}