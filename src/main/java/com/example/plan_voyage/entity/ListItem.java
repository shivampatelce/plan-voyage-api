package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class ListItem {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String item;

    private String addedBy;

    @ManyToOne
    @JoinColumn(name = "trip_list_id", nullable = false)
    @JsonIgnore
    private TripList tripList;

    public ListItem() {
    }

    public ListItem(String item, String addedBy, TripList tripList) {
        this.item = item;
        this.addedBy = addedBy;
        this.tripList = tripList;
    }

    public ListItem(UUID id, String item, TripList tripList) {
        this.id = id;
        this.item = item;
        this.tripList = tripList;
    }

    public ListItem(String item) {
        this.item = item;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public TripList getTripList() {
        return tripList;
    }

    public void setTripList(TripList tripList) {
        this.tripList = tripList;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
