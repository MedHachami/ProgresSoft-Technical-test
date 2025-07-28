package com.progressoft.technicaltest.service;

import com.progressoft.technicaltest.entity.Deal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DealValidationService implements IDealValidationService {

    public List<String> validateDeal(Deal deal) {
        List<String> errors = new ArrayList<>();

        if (deal == null) {
            errors.add("Deal must not be null");
            return errors;
        }

        if (deal.getId() == null || deal.getId().isBlank()) {
            errors.add("Deal ID is required");
        }

        if (deal.getFromCurrency() == null) {
            errors.add("From currency is required");
        }

        if (deal.getToCurrency() == null) {
            errors.add("To currency is required");
        }

        if (deal.getFromCurrency() != null && deal.getToCurrency() != null &&
                Objects.equals(deal.getFromCurrency(), deal.getToCurrency())) {
            errors.add("From currency and To currency must be different");
        }

        if (deal.getAmount() == null) {
            errors.add("Amount is required");
        } else if (deal.getAmount().signum() <= 0) {
            errors.add("Amount must be positive");
        }

        if (deal.getTimestamp() == null) {
            errors.add("Timestamp is required");
        }

        return errors;
    }
}
