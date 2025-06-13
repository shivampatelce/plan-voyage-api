package com.example.plan_voyage.dto;

import java.util.UUID;

public class TripInvitationDetailReqDto {
    private UUID invitationId;
    private String email;

    public TripInvitationDetailReqDto(UUID invitationId, String email) {
        this.invitationId = invitationId;
        this.email = email;
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
}
