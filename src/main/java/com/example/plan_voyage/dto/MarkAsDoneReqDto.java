package com.example.plan_voyage.dto;

import java.util.UUID;

public class MarkAsDoneReqDto {
    private String userId;

    private UUID taskId;

    private UUID tripId;

    public MarkAsDoneReqDto(String userId, UUID taskId, UUID tripId) {
        this.userId = userId;
        this.taskId = taskId;
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }
}
