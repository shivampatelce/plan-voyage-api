package com.example.plan_voyage.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class InviteUserRequests {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID invitationId;

    private String email;

    private Date sentAt;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip tripId;

    public InviteUserRequests() {}

    public InviteUserRequests(String email, Trip tripId, Date sentAt) {
        this.email = email;
        this.tripId = tripId;
        this.sentAt = sentAt;
    }

    public InviteUserRequests(UUID invitationId, String email, Trip tripId, Date sentAt) {
        this.invitationId = invitationId;
        this.email = email;
        this.tripId = tripId;
        this.sentAt = sentAt;
    }

    public UUID getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(UUID invitationId) {
        this.invitationId = invitationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Trip getTripId() {
        return tripId;
    }

    public void setTripId(Trip tripId) {
        this.tripId = tripId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
