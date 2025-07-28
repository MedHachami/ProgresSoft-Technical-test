package com.progressoft.technicaltest.service;

import java.util.List;

import com.progressoft.technicaltest.entity.Deal;

public interface IDealValidationService {
    public List<String> validateDeal(Deal deal);
}
