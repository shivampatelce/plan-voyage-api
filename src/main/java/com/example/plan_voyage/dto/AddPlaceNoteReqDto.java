package com.example.plan_voyage.dto;

import java.util.UUID;

public class AddPlaceNoteReqDto {
    private UUID itineraryPlaceId;

    private String notes;

    public AddPlaceNoteReqDto(UUID itineraryPlaceId, String notes) {
        this.itineraryPlaceId = itineraryPlaceId;
        this.notes = notes;
    }

    public UUID getItineraryPlaceId() {
        return itineraryPlaceId;
    }

    public void setItineraryPlaceId(UUID itineraryPlaceId) {
        this.itineraryPlaceId = itineraryPlaceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
