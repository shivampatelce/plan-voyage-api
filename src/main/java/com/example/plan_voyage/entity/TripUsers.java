package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class TripUsers {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    TripUsers() {

    }

    public TripUsers(String userId, Trip trip) {
        this.userId = userId;
        this.trip = trip;
    }

    public TripUsers(UUID id, String userId, Trip trip) {
        this.id = id;
        this.userId = userId;
        this.trip = trip;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
