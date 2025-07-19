package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID notificationId;

    private String title;

    private String message;

    private String type;

    private String actionUrl;

    private LocalDateTime timestamp;

    private UUID tripId;

    @OneToMany(mappedBy = "notificationReceiverId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<NotificationReceiver> notificationReceivers = new ArrayList<>();

    public Notification() {
    }

    public Notification(String title, String message, String type, String actionUrl, LocalDateTime timestamp, UUID tripId) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.actionUrl = actionUrl;
        this.timestamp = timestamp;
        this.tripId = tripId;
    }

    public Notification(String title, String message, String type, String actionUrl, LocalDateTime timestamp, UUID tripId, List<NotificationReceiver> notificationReceivers) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.actionUrl = actionUrl;
        this.timestamp = timestamp;
        this.tripId = tripId;
        this.notificationReceivers = notificationReceivers;
    }

    public Notification(UUID notificationId, String title, String message, String type, String actionUrl, LocalDateTime timestamp, UUID tripId, List<NotificationReceiver> notificationReceivers) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.actionUrl = actionUrl;
        this.timestamp = timestamp;
        this.tripId = tripId;
        this.notificationReceivers = notificationReceivers;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<NotificationReceiver> getNotificationReceivers() {
        return notificationReceivers;
    }

    public void setNotificationReceivers(List<NotificationReceiver> notificationReceivers) {
        this.notificationReceivers = notificationReceivers;
    }
}
