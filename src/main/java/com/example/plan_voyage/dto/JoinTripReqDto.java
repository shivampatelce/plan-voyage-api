package com.example.plan_voyage.dto;

import java.util.UUID;

public class JoinTripReqDto {
    private String userId;
    private UUID tripId;
    private UUID invitationId;

    public JoinTripReqDto(String userId, UUID tripId, UUID invitationId) {
        this.userId = userId;
        this.tripId = tripId;
        this.invitationId = invitationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public UUID getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(UUID invitationId) {
        this.invitationId = invitationId;
    }
}
