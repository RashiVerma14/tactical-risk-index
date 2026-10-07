package com.financialrisk.engine.model;

public class EventResult {

    private String eventType;

    public EventResult() {
    }

    public EventResult(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}