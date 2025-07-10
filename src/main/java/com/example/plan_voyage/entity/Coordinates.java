package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String latitude;

    private String longitude;

    @OneToOne
    @JoinColumn(name = "place_id", nullable = false)
    @JsonProperty("itineraryPlace")
    @JsonIgnore
    private ItineraryPlace itineraryPlace;

    public Coordinates() {
    }

    public Coordinates(String latitude, String longitude, ItineraryPlace itineraryPlace) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.itineraryPlace = itineraryPlace;
    }

    public Coordinates(UUID id, String latitude, String longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ItineraryPlace getItineraryPlace() {
        return itineraryPlace;
    }

    public void setItineraryPlace(ItineraryPlace itineraryPlace) {
        this.itineraryPlace = itineraryPlace;
    }
}
