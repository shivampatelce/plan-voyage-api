package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.TripResDto;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.services.ItineraryService;
import com.example.plan_voyage.services.TripService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessResponse;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@PermitAll
@RestController
@RequestMapping("/v1/shared-itinerary")
public class SharedItineraryController extends BaseController {
    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private TripService tripService;

    @PermitAll
    @GetMapping("/{tripId}")
    public ResponseEntity<SuccessResponse<List<Itinerary>>> itineraryList(@PathVariable UUID tripId) {
        List<Itinerary> itineraries;
        try {
            itineraries = itineraryService.itineraryList(tripId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/itinerary/"+tripId);
        }
        return success(itineraries, "List of itinerary.");
    }

    @PermitAll
    @PostMapping("/related-trip-list/{placeName}")
    public ResponseEntity<SuccessResponse<List<Trip>>> relatedTripList(String placeName) {
        List<Trip> tripList;
        try{
            tripList = itineraryService.relatedTripList(placeName);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/related-trip-list/" + placeName);
        }
        return success(tripList, "Related trip list");
    }

    @PermitAll
    @GetMapping("/trip-overview/{tripId}")
    public ResponseEntity<SuccessResponse<TripResDto>> tripOverView(@PathVariable UUID tripId) {
        TripResDto trip = null;
        try {
            trip = tripService.getTripByTripId(tripId);
        } catch (JSONException e) {
            return error("Something goes wrong while fetching destination image", HttpStatus.INTERNAL_SERVER_ERROR, "/trip");
        }
        return success(trip, "Trip overview");
    }
}
