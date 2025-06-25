package com.example.plan_voyage.dto;

import java.util.UUID;

public class CreateListReqDto {
    private String listTitle;
    private UUID tripId;
    private String userId;

    public CreateListReqDto(String listTitle, UUID tripId, String userId) {
        this.listTitle = listTitle;
        this.tripId = tripId;
        this.userId = userId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
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
