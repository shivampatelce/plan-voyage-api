package com.example.plan_voyage.dto;

import java.util.UUID;

public class GetSettlementsReqDto {
    private String userId;

    private UUID tripId;

    public GetSettlementsReqDto(String userId, UUID tripId) {
        this.userId = userId;
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "GetSettlementsReqDto{" +
                "userId='" + userId + '\'' +
                ", tripId=" + tripId +
                '}';
    }
}
