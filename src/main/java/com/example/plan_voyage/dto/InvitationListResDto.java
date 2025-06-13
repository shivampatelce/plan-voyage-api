package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.UUID;

public class InvitationListResDto {
    private UUID invitationId;

    private TripResDto trip;

    public InvitationListResDto(UUID invitationId, TripResDto trip) {
        this.invitationId = invitationId;
        this.trip = trip;
    }

    public UUID getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(UUID invitationId) {
        this.invitationId = invitationId;
    }

    public TripResDto getTrip() {
        return trip;
    }

    public void setTrip(TripResDto trip) {
        this.trip = trip;
    }
}
