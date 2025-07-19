package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.NotificationReceiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiver, UUID> {
    List<NotificationReceiver> findAllByUserId(String userId);
}
