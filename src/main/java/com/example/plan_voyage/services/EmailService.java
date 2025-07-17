package com.example.plan_voyage.services;

import com.example.plan_voyage.entity.Trip;

import java.util.UUID;

public interface EmailService {
    void sendInvitationEmail(String email, String subject, String senderName, Trip trip, UUID invitationId, boolean isRegistered);
}
