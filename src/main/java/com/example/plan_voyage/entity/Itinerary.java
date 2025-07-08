package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Itinerary {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID itineraryId;

    private Date date;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItineraryPlace> itineraryPlaces = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    public Itinerary() {
    }

    public Itinerary(UUID itineraryId, Date date, Trip trip) {
        this.itineraryId = itineraryId;
        this.date = date;
        this.trip = trip;
    }

    public Itinerary(Date date, List<ItineraryPlace> itineraryPlaces, Trip trip) {
        this.date = date;
        this.itineraryPlaces = itineraryPlaces;
        this.trip = trip;
    }

    public Itinerary(UUID itineraryId, Date date, List<ItineraryPlace> itineraryPlaces, Trip trip) {
        this.itineraryId = itineraryId;
        this.date = date;
        this.itineraryPlaces = itineraryPlaces;
        this.trip = trip;
    }

    public Itinerary(UUID itineraryId, Date date, List<ItineraryPlace> itineraryPlaces) {
        this.itineraryId = itineraryId;
        this.date = date;
        this.itineraryPlaces = itineraryPlaces;
    }

    public UUID getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(UUID itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ItineraryPlace> getItineraryPlaces() {
        return itineraryPlaces;
    }

    public void setItineraryPlaces(List<ItineraryPlace> itineraryPlaces) {
        this.itineraryPlaces = itineraryPlaces;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
