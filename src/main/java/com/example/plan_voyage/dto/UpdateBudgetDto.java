package com.example.plan_voyage.dto;

import java.util.UUID;

public class UpdateBudgetDto {

    private Double budget;

    private UUID tripId;

    public UpdateBudgetDto(Double budget, UUID tripId) {
        this.budget = budget;
        this.tripId = tripId;
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
}
