package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InviteUserReqDto {
    private List<String> emails;

    private UUID tripId;

    private Date sentAt;

    private String inviterId;

    public InviteUserReqDto(List<String> emails, UUID tripId, Date sentAt, String inviterId) {
        this.emails = emails;
        this.tripId = tripId;
        this.sentAt = sentAt;
        this.inviterId = inviterId;
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

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }
}
