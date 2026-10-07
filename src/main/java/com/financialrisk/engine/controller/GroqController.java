package com.financialrisk.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.AIAnalysisResult;
import com.financialrisk.engine.service.GroqService;

@RestController
public class GroqController {

    private final GroqService groqService;

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @GetMapping("/api/groq")
    public AIAnalysisResult analyze(
            @RequestParam String text) {

        return groqService.analyzeText(text);
    }
}