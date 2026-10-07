package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.NewsArticle;
import com.financialrisk.engine.service.NewsApiService;

@RestController
public class NewsApiController {

    private final NewsApiService newsApiService;

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    @GetMapping("/api/news/newsapi")
    public List<NewsArticle> getNewsFromNewsApi() {
        return newsApiService.getLatestNews();
    }
}