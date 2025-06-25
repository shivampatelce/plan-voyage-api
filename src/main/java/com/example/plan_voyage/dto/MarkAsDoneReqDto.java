package com.example.plan_voyage.dto;

import java.util.UUID;

public class MarkAsDoneReqDto {
    private String userId;
    private UUID taskId;

    public MarkAsDoneReqDto(String userId, UUID taskId) {
        this.userId = userId;
        this.taskId = taskId;
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
}
