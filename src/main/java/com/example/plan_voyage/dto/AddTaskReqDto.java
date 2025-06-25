package com.example.plan_voyage.dto;

import java.util.UUID;

public class AddTaskReqDto {
    private String taskTitle;

    private String createdBy;

    private UUID tripId;

    public AddTaskReqDto(String taskTitle, String createdBy, UUID tripId) {
        this.taskTitle = taskTitle;
        this.createdBy = createdBy;
        this.tripId = tripId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }
}
