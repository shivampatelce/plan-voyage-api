package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.AddItineraryRatingReqDto;
import com.example.plan_voyage.dto.AddItineraryReqDto;
import com.example.plan_voyage.dto.AddPlaceNoteReqDto;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.ItineraryPlace;
import com.example.plan_voyage.entity.ItineraryRating;
import com.example.plan_voyage.services.ItineraryService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/itinerary")
public class ItineraryController extends BaseController {

    @Autowired
    private ItineraryService itineraryService;

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

    @PostMapping("/add-itinerary")
    public ResponseEntity<SuccessResponse<Itinerary>> addItinerary(@RequestBody AddItineraryReqDto addItineraryReqDto) {
        Itinerary itinerary;
        try {
            itinerary = itineraryService.addItinerary(addItineraryReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/add-itinerary");
        }
        return success(itinerary, "Itinerary has been added.");
    }

    @PutMapping("/add-notes")
    public ResponseEntity<SuccessResponse<ItineraryPlace>> addNotes(@RequestBody AddPlaceNoteReqDto addPlaceNoteReqDto) {
        ItineraryPlace itineraryPlace;
        try {
            itineraryPlace = itineraryService.addPlaceNote(addPlaceNoteReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/add-notes");
        }
        return success(itineraryPlace, "Itinerary place notes has been added.");
    }

    @DeleteMapping("/remove-place/{placeId}")
    public ResponseEntity<SuccessMessageResponse> removePlace(@PathVariable UUID placeId) {
        itineraryService.removePlace(placeId);
        return success("Place has been removed.");
    }

    @PostMapping("/add-itinerary-rating")
    public ResponseEntity<SuccessResponse<ItineraryRating>> addItineraryRating(@RequestBody AddItineraryRatingReqDto addItineraryRatingReqDto) {
        ItineraryRating itineraryRating;
        try {
            itineraryRating = itineraryService.addItineraryRating(addItineraryRatingReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/add-itinerary-rating");
        }
        return success(itineraryRating, "Rating for itinerary has been added.");
    }

    @GetMapping("/itinerary-rating-list/{tripId}")
    public ResponseEntity<SuccessResponse<List<ItineraryRating>>> itineraryRatingList(@PathVariable UUID tripId) {
        List<ItineraryRating> itineraryRatings;
        try {
            itineraryRatings = itineraryService.getItineraryRatingList(tripId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/itinerary-rating-list/"+tripId);
        }
        return success(itineraryRatings, "Itinerary rating list");
    }

}
