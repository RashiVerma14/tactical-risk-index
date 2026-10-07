package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.RiskSignal;
import com.financialrisk.engine.service.RiskEngineService;

@RestController
public class RiskEngineController {

    private final RiskEngineService riskEngineService;

    public RiskEngineController(
            RiskEngineService riskEngineService) {

        this.riskEngineService = riskEngineService;
    }

    @GetMapping("/api/risk/signals")
    public List<RiskSignal> getRiskSignals() {

        return riskEngineService.analyzeNews();
    }

    @PostMapping("/api/risk/refresh")
    public String refreshRiskAnalysis() {

        riskEngineService.clearCache();

        return "Risk analysis cache cleared. Next request will run fresh AI analysis.";
    }
}