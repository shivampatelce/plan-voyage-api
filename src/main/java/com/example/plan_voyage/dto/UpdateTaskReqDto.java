package com.example.plan_voyage.dto;

import java.util.UUID;

public class UpdateTaskReqDto {
    private UUID taskId;

    private String taskTitle;

    private UUID tripId;

    private String userId;

    public UpdateTaskReqDto(UUID taskId, String taskTitle, UUID tripId, String userId) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.tripId = tripId;
        this.userId = userId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
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
