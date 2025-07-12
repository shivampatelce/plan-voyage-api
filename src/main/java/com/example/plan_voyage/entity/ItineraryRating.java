package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class ItineraryRating {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID itineraryRatingId;

    private int rating;

    private String comment;

    private String commenterId;

    private String commenterFullName;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    public ItineraryRating() {
    }

    public ItineraryRating(int rating, String comment, String commenterId, String commenterFullName, Trip trip) {
        this.rating = rating;
        this.comment = comment;
        this.commenterId = commenterId;
        this.commenterFullName = commenterFullName;
        this.trip = trip;
    }

    public ItineraryRating(UUID itineraryRatingId, int rating, String comment, String commenterId, String commenterFullName, Trip trip) {
        this.itineraryRatingId = itineraryRatingId;
        this.rating = rating;
        this.comment = comment;
        this.commenterId = commenterId;
        this.commenterFullName = commenterFullName;
        this.trip = trip;
    }

    public UUID getItineraryRatingId() {
        return itineraryRatingId;
    }

    public void setItineraryRatingId(UUID itineraryRatingId) {
        this.itineraryRatingId = itineraryRatingId;
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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getCommenterFullName() {
        return commenterFullName;
    }

    public void setCommenterFullName(String commenterFullName) {
        this.commenterFullName = commenterFullName;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }
}
