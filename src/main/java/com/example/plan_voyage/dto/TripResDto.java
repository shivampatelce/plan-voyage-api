package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.UUID;

public class TripResDto {
    private UUID tripId;

    private String destination;

    private Date startDate;

    private Date endDate;

    private String userId;

    private String destinationImageUrl;

    public TripResDto(UUID tripId, String destination, Date startDate, Date endDate, String userId, String destinationImageUrl) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.destinationImageUrl = destinationImageUrl;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestinationImageUrl() {
        return destinationImageUrl;
    }

    public void setDestinationImageUrl(String destinationImageUrl) {
        this.destinationImageUrl = destinationImageUrl;
    }
}
