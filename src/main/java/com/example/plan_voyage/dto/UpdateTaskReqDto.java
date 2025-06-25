package com.example.plan_voyage.dto;

import java.util.UUID;

public class UpdateTaskReqDto {
    private UUID taskId;
    private String taskTitle;

    public UpdateTaskReqDto(UUID taskId, String taskTitle) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
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
}
