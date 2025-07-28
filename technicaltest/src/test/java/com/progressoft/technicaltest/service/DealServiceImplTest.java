package com.progressoft.technicaltest.service;

import com.progressoft.technicaltest.dto.DealRequestDto;
import com.progressoft.technicaltest.dto.DealResponseDto;
import com.progressoft.technicaltest.entity.Deal;
import com.progressoft.technicaltest.exception.CurrencyMismatchException;
import com.progressoft.technicaltest.mapper.DealMapper;
import com.progressoft.technicaltest.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealMapper dealMapper;

    private IDealService dealService;

    private DealRequestDto requestDto;
    private Deal deal;

    @BeforeEach
    void setUp() {
        dealService = new DealServiceImpl(dealRepository, dealMapper);

        requestDto = new DealRequestDto(
                "deal123",
                Currency.getInstance("USD"),
                Currency.getInstance("EUR"),
                LocalDateTime.now(),
                BigDecimal.valueOf(1000));

        deal = new Deal(
                requestDto.getId(),
                requestDto.getFromCurrency(),
                requestDto.getToCurrency(),
                requestDto.getTimestamp(),
                requestDto.getAmount());
    }

    @Test
    void givenValidRequest_whenSave_thenReturnsResponse() {
        DealResponseDto responseDto = new DealResponseDto(
                deal.getId(),
                deal.getFromCurrency(),
                deal.getToCurrency(),
                deal.getTimestamp(),
                deal.getAmount());

        given(dealRepository.existsById(requestDto.getId())).willReturn(false);
        given(dealMapper.toEntity(requestDto)).willReturn(deal);
        given(dealRepository.save(deal)).willReturn(deal);
        given(dealMapper.toResponseEntity(deal)).willReturn(responseDto);

        DealResponseDto result = dealService.save(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("deal123");
        verify(dealRepository).save(any(Deal.class));
    }

    @Test
    void givenDuplicateDealId_whenSave_thenThrowsException() {
        given(dealRepository.existsById(requestDto.getId())).willReturn(true);

        assertThatExceptionOfType(CurrencyMismatchException.class)
                .isThrownBy(() -> dealService.save(requestDto))
                .withMessage("Deal id already exists");
    }

    @Test
    void givenNullAmount_whenSave_thenThrowsException() {
        requestDto.setAmount(null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> dealService.save(requestDto));
    }

    @Test
    void givenZeroAmount_whenSave_thenAccept() {
        requestDto.setAmount(BigDecimal.ZERO);

        Deal zeroDeal = new Deal(
                requestDto.getId(),
                requestDto.getFromCurrency(),
                requestDto.getToCurrency(),
                requestDto.getTimestamp(),
                requestDto.getAmount());

        DealResponseDto responseDto = new DealResponseDto(
                zeroDeal.getId(),
                zeroDeal.getFromCurrency(),
                zeroDeal.getToCurrency(),
                zeroDeal.getTimestamp(),
                zeroDeal.getAmount());

        given(dealRepository.existsById(requestDto.getId())).willReturn(false);
        given(dealMapper.toEntity(requestDto)).willReturn(zeroDeal);
        given(dealRepository.save(zeroDeal)).willReturn(zeroDeal);
        given(dealMapper.toResponseEntity(zeroDeal)).willReturn(responseDto);

        DealResponseDto result = dealService.save(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.amount()).isEqualByComparingTo("0");
    }

    @Test
    void givenSameCurrencyForFromAndTo_whenSave_thenAccept() {
        Currency currency = Currency.getInstance("USD");
        requestDto.setFromCurrency(currency);
        requestDto.setToCurrency(currency);

        Deal sameCurrencyDeal = new Deal(
                requestDto.getId(),
                currency,
                currency,
                requestDto.getTimestamp(),
                requestDto.getAmount());

        DealResponseDto responseDto = new DealResponseDto(
                sameCurrencyDeal.getId(),
                sameCurrencyDeal.getFromCurrency(),
                sameCurrencyDeal.getToCurrency(),
                sameCurrencyDeal.getTimestamp(),
                sameCurrencyDeal.getAmount());

        given(dealRepository.existsById(requestDto.getId())).willReturn(false);
        given(dealMapper.toEntity(requestDto)).willReturn(sameCurrencyDeal);
        given(dealRepository.save(sameCurrencyDeal)).willReturn(sameCurrencyDeal);
        given(dealMapper.toResponseEntity(sameCurrencyDeal)).willReturn(responseDto);

        DealResponseDto result = dealService.save(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.fromCurrency()).isEqualTo(result.toCurrency());
    }

    @Test
    void givenNullCurrency_whenSave_thenThrowsException() {
        requestDto.setFromCurrency(null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> dealService.save(requestDto));
    }

    @Test
    void givenNegativeAmount_whenSave_thenAcceptOrRejectBasedOnBusinessLogic() {
        BigDecimal negativeAmount = BigDecimal.valueOf(-100);
        requestDto.setAmount(negativeAmount);

        Deal negativeDeal = new Deal(
                requestDto.getId(),
                requestDto.getFromCurrency(),
                requestDto.getToCurrency(),
                requestDto.getTimestamp(),
                negativeAmount);

        DealResponseDto responseDto = new DealResponseDto(
                negativeDeal.getId(),
                negativeDeal.getFromCurrency(),
                negativeDeal.getToCurrency(),
                negativeDeal.getTimestamp(),
                negativeDeal.getAmount());

        given(dealRepository.existsById(requestDto.getId())).willReturn(false);
        given(dealMapper.toEntity(requestDto)).willReturn(negativeDeal);
        given(dealRepository.save(negativeDeal)).willReturn(negativeDeal);
        given(dealMapper.toResponseEntity(negativeDeal)).willReturn(responseDto);

        DealResponseDto result = dealService.save(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.amount()).isEqualByComparingTo("-100");
    }
}
