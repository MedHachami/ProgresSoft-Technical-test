package com.progressoft.technicaltest.mapper;

import com.progressoft.technicaltest.dto.DealRequestDto;
import com.progressoft.technicaltest.dto.DealResponseDto;
import com.progressoft.technicaltest.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DealMapper {
    @Mapping(target = "fromCurrency", source = "fromCurrency")
    @Mapping(target = "toCurrency", source = "toCurrency")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "amount", source = "amount")
    Deal toEntity(DealRequestDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fromCurrency", source = "fromCurrency")
    @Mapping(target = "toCurrency", source = "toCurrency")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "amount", source = "amount")
    DealResponseDto toResponseEntity(Deal deal);
}
