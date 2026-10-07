package com.financialrisk.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.SentimentResult;
import com.financialrisk.engine.service.SentimentService;

@RestController
public class SentimentController {

    private final SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @GetMapping("/api/sentiment")
    public SentimentResult analyzeSentiment(
            @RequestParam String text) {

        return sentimentService.analyze(text);
    }
}