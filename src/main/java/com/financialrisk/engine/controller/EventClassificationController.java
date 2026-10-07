package com.financialrisk.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.EventResult;
import com.financialrisk.engine.service.EventClassificationService;

@RestController
public class EventClassificationController {

    private final EventClassificationService eventClassificationService;

    public EventClassificationController(
            EventClassificationService eventClassificationService) {

        this.eventClassificationService = eventClassificationService;
    }

    @GetMapping("/api/event")
    public EventResult classifyEvent(
            @RequestParam String text) {

        return eventClassificationService.classify(text);
    }
}