package com.progressoft.technicaltest.service;

import com.progressoft.technicaltest.dto.DealRequestDto;
import com.progressoft.technicaltest.dto.DealResponseDto;

public interface IDealService {
    DealResponseDto save(DealRequestDto dto);
}