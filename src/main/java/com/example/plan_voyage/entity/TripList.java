package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TripList {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID tripListId;

    private String title;

    private String userId;

    @OneToMany(mappedBy = "tripList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    public TripList() {
    }

    public TripList(UUID tripListId, String title, String userId, List<ListItem> items, Trip trip) {
        this.tripListId = tripListId;
        this.title = title;
        this.userId = userId;
        this.items = items;
        this.trip = trip;
    }

    public TripList(String title, String userId, List<ListItem> items, Trip trip) {
        this.title = title;
        this.userId = userId;
        this.items = items;
        this.trip = trip;
    }

    public UUID getTripListId() {
        return tripListId;
    }

    public void setTripListId(UUID tripListId) {
        this.tripListId = tripListId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
