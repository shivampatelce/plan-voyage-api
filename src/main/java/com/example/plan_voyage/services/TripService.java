package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.CreateTripReqDto;
import com.example.plan_voyage.dto.TripListResDto;
import com.example.plan_voyage.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface TripService {
    Trip createTrip(CreateTripReqDto createTripReqDto);

    List<TripListResDto> getTripsByUserId(String userId);

    void removeTripById(UUID tripId);
}
