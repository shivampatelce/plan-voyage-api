package com.example.plan_voyage.dto;

import java.util.UUID;

public class SetBudgetReqDto {
    private Double budget;

    private UUID tripId;

    private String userId;

    public SetBudgetReqDto(Double budget, UUID tripId, String userId) {
        this.budget = budget;
        this.tripId = tripId;
        this.userId = userId;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SetBudgetReqDto{" +
                "budget=" + budget +
                ", tripId=" + tripId +
                '}';
    }
}
