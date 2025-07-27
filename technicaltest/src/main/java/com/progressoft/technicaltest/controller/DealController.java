package com.progressoft.technicaltest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
public class DealController {
    
    @GetMapping
    public String getDeal(){
        return "return deals";
    }
}
