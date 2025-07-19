package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class NotificationReceiver {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID notificationReceiverId;

    private String userId;

    private boolean seen;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    @JsonProperty("notification")
    private Notification notification;

    public NotificationReceiver() {
    }

    public NotificationReceiver(String userId, Notification notification) {
        this.userId = userId;
        this.notification = notification;
    }

    public NotificationReceiver(UUID notificationReceiverId, String userId, Notification notification) {
        this.notificationReceiverId = notificationReceiverId;
        this.userId = userId;
        this.notification = notification;
    }

    public NotificationReceiver(UUID notificationReceiverId, String userId, boolean seen, Notification notification) {
        this.notificationReceiverId = notificationReceiverId;
        this.userId = userId;
        this.seen = seen;
        this.notification = notification;
    }

    public UUID getNotificationReceiverId() {
        return notificationReceiverId;
    }

    public void setNotificationReceiverId(UUID notificationReceiverId) {
        this.notificationReceiverId = notificationReceiverId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
