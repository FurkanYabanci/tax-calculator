package com.yabanci.ayrotek.controller;

import com.yabanci.ayrotek.dto.GeneralResponseDto;
import com.yabanci.ayrotek.dto.ProductDto;
import com.yabanci.ayrotek.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotek.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotek.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity findAll(){
        List<ProductDto> productDtoList = productService.findAll();
        return ResponseEntity.ok(GeneralResponseDto.of(productDtoList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        ProductDto productDto = productService.save(productSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        ProductDto productDto = productService.update(productUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        productService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("Product Deleted!"));
    }
}