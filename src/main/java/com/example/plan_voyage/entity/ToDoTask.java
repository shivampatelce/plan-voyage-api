package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class ToDoTask {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID taskId;

    private String taskTitle;

    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    private String markedDoneBy;

    public ToDoTask() {
    }

    public ToDoTask(String taskTitle, String createdBy, Trip trip) {
        this.taskTitle = taskTitle;
        this.createdBy = createdBy;
        this.trip = trip;
    }

    public ToDoTask(UUID taskId, String taskTitle, String createdBy, Trip trip) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.createdBy = createdBy;
        this.trip = trip;
    }

    public ToDoTask(UUID taskId, String taskTitle, String createdBy, Trip trip, String markedDoneBy) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.createdBy = createdBy;
        this.trip = trip;
        this.markedDoneBy = markedDoneBy;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getMarkedDoneBy() {
        return markedDoneBy;
    }

    public void setMarkedDoneBy(String markedDoneBy) {
        this.markedDoneBy = markedDoneBy;
    }
}
