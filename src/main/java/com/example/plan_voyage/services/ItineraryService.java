package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.AddItineraryRatingReqDto;
import com.example.plan_voyage.dto.AddItineraryReqDto;
import com.example.plan_voyage.dto.AddPlaceNoteReqDto;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.ItineraryPlace;
import com.example.plan_voyage.entity.ItineraryRating;
import com.example.plan_voyage.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface ItineraryService {
    List<Itinerary> itineraryList(UUID tripId);

    Itinerary addItinerary(AddItineraryReqDto addItineraryReqDto);

    void removePlace(UUID placeId);

    ItineraryPlace addPlaceNote(AddPlaceNoteReqDto addPlaceNoteReqDto);

    ItineraryRating addItineraryRating(AddItineraryRatingReqDto addItineraryRatingReqDto);

    List<ItineraryRating> getItineraryRatingList(UUID tripId);
}
