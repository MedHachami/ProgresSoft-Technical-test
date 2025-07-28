package com.progressoft.technicaltest.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressoft.technicaltest.entity.Deal;
import com.progressoft.technicaltest.repository.DealRepository;
import com.progressoft.technicaltest.service.DealValidationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
@Slf4j
public class DealController {

    private final DealRepository dealRepository;
    private final DealValidationService validationService;

    @PostMapping
    public ResponseEntity<?> saveDeal(@RequestBody Deal deal) {
        log.info("Received deal: {}", deal);

        List<String> validationErrors = validationService.validateDeal(deal);
        if (!validationErrors.isEmpty()) {
            log.warn("Validation failed for deal {}: {}", deal.getId(), validationErrors);
            return ResponseEntity.badRequest().body(Map.of("errors", validationErrors));
        }

        try {
            if (dealRepository.existsById(deal.getId())) {
                log.warn("Duplicate deal detected with ID: {}", deal.getId());
                return ResponseEntity.badRequest().body("Duplicate deal ID: " + deal.getId());
            }

            dealRepository.save(deal);
            log.info("Deal saved successfully with ID: {}", deal.getId());
            return ResponseEntity.ok("Deal saved successfully : "+deal.getId());

        } catch (Exception ex) {
            log.error("Error while saving deal", ex);
            throw ex;
        }
    }

}