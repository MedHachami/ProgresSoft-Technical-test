package com.progressoft.technicaltest.controller;


import com.progressoft.technicaltest.dto.DealRequestDto;
import com.progressoft.technicaltest.dto.DealResponseDto;
import com.progressoft.technicaltest.service.DealService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
@Tag(name = "Deals", description = "Endpoints for managing currency deals")
public class DealController {
    private final DealService dealService;

    @Operation(summary = "Create a new deal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deal saved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Deal ID already exists")
    })
    @PostMapping
    public ResponseEntity<DealResponseDto> save(@RequestBody @Valid DealRequestDto dto) {
        DealResponseDto deal = dealService.save(dto);

        return new ResponseEntity<>(deal, HttpStatus.CREATED);
    }
}