package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.UUID;

public class AddItineraryReqDto {
    private UUID tripId;

    private UUID itineraryId;

    private String place;

    private String category;

    private String time;

    private Date date;

    private String userId;

    public AddItineraryReqDto(UUID tripId, UUID itineraryId, String place, String category, String time, Date date, String userId) {
        this.tripId = tripId;
        this.itineraryId = itineraryId;
        this.place = place;
        this.category = category;
        this.time = time;
        this.date = date;
        this.userId = userId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public UUID getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(UUID itineraryId) {
        this.itineraryId = itineraryId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
