package com.example.plan_voyage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class Trip {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID tripId;

    private String destination;

    private Date startDate;

    private Date endDate;

    private String userId;

    public Trip() {}

    public Trip(String destination, Date startDate, Date endDate, String userId) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
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
}
