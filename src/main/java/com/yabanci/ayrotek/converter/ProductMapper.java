package com.yabanci.ayrotek.converter;

import com.yabanci.ayrotek.dto.ProductDto;
import com.yabanci.ayrotek.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotek.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product convertToProduct(ProductSaveRequestDto productSaveRequestDto);
    ProductDto convertToProductDto(Product product);
    List<ProductDto> convertToProductDtoList(List<Product> products);
}
