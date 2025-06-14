package com.example.plan_voyage.dto;

import java.util.UUID;

public class ExitTripReqDto {
    private UUID tripId;
    private String userId;

    public ExitTripReqDto(UUID tripId, String userId) {
        this.tripId = tripId;
        this.userId = userId;
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
}
