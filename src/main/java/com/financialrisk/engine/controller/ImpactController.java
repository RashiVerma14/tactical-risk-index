package com.financialrisk.engine.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.service.ImpactScoreService;

@RestController
public class ImpactController {

    private final ImpactScoreService impactScoreService;

    public ImpactController(
            ImpactScoreService impactScoreService) {

        this.impactScoreService = impactScoreService;
    }

    @GetMapping("/api/impact")
    public Map<String, Object> calculateImpact(
            @RequestParam String text,
            @RequestParam String eventType) {

        int score = impactScoreService.calculateImpact(
                text,
                eventType
        );

        return Map.of(
                "impactScore", score
        );
    }
}