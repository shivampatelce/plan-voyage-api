package com.example.plan_voyage.dto;

public class TripListDto {
    private String userId;

    public TripListDto(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
