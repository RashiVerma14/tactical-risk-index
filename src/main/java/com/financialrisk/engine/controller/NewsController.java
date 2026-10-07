package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.NewsArticle;
import com.financialrisk.engine.service.NewsIngestionService;

@RestController
public class NewsController {

    private final NewsIngestionService newsIngestionService;

    public NewsController(NewsIngestionService newsIngestionService) {
        this.newsIngestionService = newsIngestionService;
    }

    @GetMapping("/api/news")
    public List<NewsArticle> getNews() {
        return newsIngestionService.getLatestNews();
    }
}