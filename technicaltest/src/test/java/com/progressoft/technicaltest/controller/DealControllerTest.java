package com.progressoft.technicaltest.controller;

import com.progressoft.technicaltest.entity.Deal;
import com.progressoft.technicaltest.repository.DealRepository;
import com.progressoft.technicaltest.service.DealValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DealControllerTest {

    private DealRepository dealRepository;
    private DealValidationService validationService;
    private DealController dealController;

    @BeforeEach
    void setup() {
        dealRepository = mock(DealRepository.class);
        validationService = mock(DealValidationService.class);
        dealController = new DealController(dealRepository, validationService);
    }

    private Deal getValidDeal() {
        Deal deal = new Deal();
        deal.setId("D-123");
        deal.setFromCurrency(Currency.getInstance("USD"));
        deal.setToCurrency(Currency.getInstance("EUR"));
        deal.setAmount(BigDecimal.valueOf(1000));
        deal.setTimestamp(LocalDateTime.now());
        return deal;
    }

    @Test
    void givenValidDeal_whenSaveDeal_thenReturnOk() {
        Deal deal = getValidDeal();

        when(validationService.validateDeal(deal)).thenReturn(List.of());
        when(dealRepository.existsById(deal.getId())).thenReturn(false);

        ResponseEntity<?> response = dealController.saveDeal(deal);

        verify(dealRepository).save(deal);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Deal saved successfully"));
    }

    @Test
    void givenInvalidDeal_whenSaveDeal_thenReturnBadRequest() {
        Deal deal = getValidDeal();
        List<String> errors = List.of("Amount must be positive");

        when(validationService.validateDeal(deal)).thenReturn(errors);

        ResponseEntity<?> response = dealController.saveDeal(deal);

        verify(dealRepository, never()).save(any());
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(((Map<?, ?>) response.getBody()).get("errors").toString().contains("Amount must be positive"));
    }

    @Test
    void givenDuplicateDealId_whenSaveDeal_thenReturnBadRequest() {
        Deal deal = getValidDeal();

        when(validationService.validateDeal(deal)).thenReturn(List.of());
        when(dealRepository.existsById(deal.getId())).thenReturn(true);

        ResponseEntity<?> response = dealController.saveDeal(deal);

        verify(dealRepository, never()).save(any());
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Duplicate deal ID"));
    }

    @Test
    void givenRepoThrowsException_whenSaveDeal_thenThrow() {
        Deal deal = getValidDeal();

        when(validationService.validateDeal(deal)).thenReturn(List.of());
        when(dealRepository.existsById(deal.getId())).thenReturn(false);
        when(dealRepository.save(deal)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> dealController.saveDeal(deal));
        assertEquals("Database error", exception.getMessage());
    }
}
