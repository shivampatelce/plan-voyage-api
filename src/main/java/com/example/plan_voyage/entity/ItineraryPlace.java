package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class ItineraryPlace {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String place;

    private String category;

    private String time;

    private String notes;

    @OneToOne(mappedBy = "itineraryPlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Coordinates coordinates;

    @ManyToOne
    @JoinColumn(name = "itinerary_id", nullable = false)
    @JsonProperty("itinerary")
    @JsonIgnore
    private Itinerary itinerary;

    public ItineraryPlace() {
    }

    public ItineraryPlace(String place, String category, String time, Itinerary itinerary) {
        this.place = place;
        this.category = category;
        this.time = time;
        this.itinerary = itinerary;
    }

    public ItineraryPlace(UUID id, String place, String category, String time, Itinerary itinerary) {
        this.id = id;
        this.place = place;
        this.category = category;
        this.time = time;
        this.itinerary = itinerary;
    }

    public ItineraryPlace(UUID id, String place, String category, String time, String notes, Coordinates coordinates, Itinerary itinerary) {
        this.id = id;
        this.place = place;
        this.category = category;
        this.time = time;
        this.notes = notes;
        this.coordinates = coordinates;
        this.itinerary = itinerary;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
