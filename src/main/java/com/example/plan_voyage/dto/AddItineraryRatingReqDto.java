package com.example.plan_voyage.dto;

import java.util.UUID;

public class AddItineraryRatingReqDto {

    private UUID itineraryRatingId;

    private UUID tripId;

    private int rating;

    private String comment;

    private String userId;

    public AddItineraryRatingReqDto(UUID tripId, int rating, String comment, String userId) {
        this.tripId = tripId;
        this.rating = rating;
        this.comment = comment;
        this.userId = userId;
    }

    public UUID getItineraryRatingId() {
        return itineraryRatingId;
    }

    public void setItineraryRatingId(UUID itineraryRatingId) {
        this.itineraryRatingId = itineraryRatingId;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
