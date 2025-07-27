package com.progressoft.technicaltest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.progressoft.technicaltest.config.CurrencyDeserializer;
import com.progressoft.technicaltest.validation.interfaces.DifferentCurrencies;

@DifferentCurrencies
public record DealRequestDto(@NotBlank String id,

                             @NotNull
                            @JsonDeserialize(using = CurrencyDeserializer.class)
                            Currency fromCurrency,

                            @NotNull
                            @JsonDeserialize(using = CurrencyDeserializer.class)
                            Currency toCurrency,

                             @NotNull LocalDateTime timestamp,

                             @NotNull @Positive BigDecimal amount
) {
}