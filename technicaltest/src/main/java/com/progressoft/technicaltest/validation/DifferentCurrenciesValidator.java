package com.progressoft.technicaltest.validation;


import com.progressoft.technicaltest.dto.DealRequestDto;
import com.progressoft.technicaltest.validation.interfaces.DifferentCurrencies;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentCurrenciesValidator implements ConstraintValidator<DifferentCurrencies, DealRequestDto> {

    @Override
    public boolean isValid(DealRequestDto dto, ConstraintValidatorContext context) {
        // @NotNull handle null
        if (dto == null) return true;  

        // @NotNull on fields handles this
        if (dto.fromCurrency() == null || dto.toCurrency() == null) return true;  

        return !dto.fromCurrency().equals(dto.toCurrency());
    }
}