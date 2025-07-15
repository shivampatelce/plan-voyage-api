package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.UUID;

public class UpdateTripDatesDto {
    private UUID tripId;

    private Date startDate;

    private Date endDate;

    public UpdateTripDatesDto(UUID tripId, Date startDate, Date endDate) {
        this.tripId = tripId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
