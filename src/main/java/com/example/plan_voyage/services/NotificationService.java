package com.example.plan_voyage.services;

import com.example.plan_voyage.entity.Notification;
import com.example.plan_voyage.entity.NotificationReceiver;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    void sendNotification(Notification notification, List<String> notificationReceiverIds);

    List<NotificationReceiver> getNotificationsByUserId(String userId);

    void markNotificationSeen(UUID notificationId, String userId);
}
