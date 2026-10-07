package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.NewsArticle;
import com.financialrisk.engine.service.UnifiedNewsService;

@RestController
public class UnifiedNewsController {

    private final UnifiedNewsService unifiedNewsService;

    public UnifiedNewsController(
            UnifiedNewsService unifiedNewsService) {

        this.unifiedNewsService = unifiedNewsService;
    }

    @GetMapping("/api/news/all")
    public List<NewsArticle> getAllNews() {

        return unifiedNewsService.getAllNews();
    }
}