package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.CreateTripReqDto;
import com.example.plan_voyage.dto.TripListDto;
import com.example.plan_voyage.dto.TripListResDto;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.services.TripService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/trip")
public class TripController extends BaseController {

    @Autowired
    private TripService tripService;

    @PostMapping("/create-trip")
    public ResponseEntity<SuccessResponse<Trip>> createTrip(@RequestBody CreateTripReqDto createTripDto) {
        Trip trip = tripService.createTrip(createTripDto);
        return success(trip,"New trip has been created.");
    }

    @PostMapping("/trip-list")
    public ResponseEntity<SuccessResponse<List<TripListResDto>>> trips(@RequestBody TripListDto tripListDto) {
        List<TripListResDto> trips = tripService.getTripsByUserId(tripListDto.getUserId());
        return success(trips, "List of trips");
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<SuccessMessageResponse> removeTrip(@PathVariable UUID tripId) {
        tripService.removeTripById(tripId);
        return success("Trip removed with id: " + tripId);
    }

}
