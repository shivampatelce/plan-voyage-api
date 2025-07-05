package com.example.plan_voyage.dto;

public class SettlementsResDto {
    private String userId;
    private Double settlementAmount;

    public SettlementsResDto(String userId, Double settlementAmount) {
        this.userId = userId;
        this.settlementAmount = settlementAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
}
