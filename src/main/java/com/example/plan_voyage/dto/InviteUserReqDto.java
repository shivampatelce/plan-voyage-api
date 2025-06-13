package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InviteUserReqDto {
    private List<String> emails;

    private UUID tripId;

    private Date sentAt;

    public InviteUserReqDto(List<String> emails, UUID tripId, Date sentAt) {
        this.emails = emails;
        this.tripId = tripId;
        this.sentAt = sentAt;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
