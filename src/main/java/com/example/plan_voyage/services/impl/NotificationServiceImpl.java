package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.entity.Notification;
import com.example.plan_voyage.entity.NotificationReceiver;
import com.example.plan_voyage.repository.NotificationReceiverRepository;
import com.example.plan_voyage.repository.NotificationRepository;
import com.example.plan_voyage.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private  NotificationRepository notificationRepository;

    @Autowired
    private NotificationReceiverRepository notificationReceiverRepository;

    @Override
    public void sendNotification(Notification notification, List<String> notificationReceiverIds) {
        Notification newNotification = notificationRepository.save(notification);

        notificationReceiverIds.forEach((id)->{
            notificationReceiverRepository.save(new NotificationReceiver(id, newNotification));
        });
    }

    @Override
    public List<NotificationReceiver> getNotificationsByUserId(String userId) {
        return notificationReceiverRepository.findAllByUserId(userId);
    }

    @Override
    public void markNotificationSeen(UUID notificationId, String userId) {
        List<NotificationReceiver> notificationReceivers = notificationReceiverRepository.findAllByUserId(userId);

        NotificationReceiver notificationReceiver = notificationReceivers.stream()
                .filter((receiver)->receiver.getNotification().getNotificationId().equals(notificationId))
                .toList()
                .get(0);

        notificationReceiver.setSeen(true);

        notificationReceiverRepository.save(notificationReceiver);
    }
}
