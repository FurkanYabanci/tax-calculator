package com.yabanci.ayrotek.converter;

import com.yabanci.ayrotek.dto.TaxDto;
import com.yabanci.ayrotek.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotek.model.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    Tax convertToTax(TaxSaveRequestDto taxSaveRequestDto);
    TaxDto convertToTaxDto(Tax tax);

    List<TaxDto> convertToTaxDtoList(List<Tax> taxes);
}