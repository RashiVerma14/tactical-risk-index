package com.financialrisk.engine.model;

public class ImpactResult {

    private int impactScore;

    public ImpactResult() {
    }

    public ImpactResult(int impactScore) {
        this.impactScore = impactScore;
    }

    public int getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(int impactScore) {
        this.impactScore = impactScore;
    }
}