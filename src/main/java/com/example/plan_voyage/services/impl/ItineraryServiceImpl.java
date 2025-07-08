package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddItineraryReqDto;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.ItineraryPlace;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.ItineraryPlaceRepository;
import com.example.plan_voyage.repository.ItineraryRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private ItineraryPlaceRepository itineraryPlaceRepository;

    @Override
    public List<Itinerary> itineraryList(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));

        return itineraryRepository.findAllByTrip(trip);
    }

    @Override
    public Itinerary addItinerary(AddItineraryReqDto addItineraryReqDto) {
        Itinerary itinerary;

        Trip trip = tripRepository.findById(addItineraryReqDto.getTripId())
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));

        if(addItineraryReqDto.getItineraryId() != null) {
            itinerary = itineraryRepository.findById(addItineraryReqDto.getItineraryId())
                    .orElseThrow(()->new RuntimeException("Invalid itinerary id: " + addItineraryReqDto.getItineraryId()));

            ItineraryPlace newItineraryPlace = itineraryPlaceRepository.save(new ItineraryPlace(addItineraryReqDto.getPlace(),
                    addItineraryReqDto.getCategory(),
                    addItineraryReqDto.getTime(),
                    itinerary
            ));

            itinerary.getItineraryPlaces().add(newItineraryPlace);
        } else {
            itinerary = itineraryRepository.save(new Itinerary(addItineraryReqDto.getItineraryId(), addItineraryReqDto.getDate(), trip));

            ItineraryPlace newItineraryPlace = itineraryPlaceRepository.save(new ItineraryPlace(addItineraryReqDto.getPlace(),
                                                                addItineraryReqDto.getCategory(),
                                                                addItineraryReqDto.getTime(),
                                                                itinerary
                                                            ));

            itinerary.setItineraryPlaces(List.of(newItineraryPlace));
        }
        return itinerary;
    }

    @Override
    public void removePlace(UUID placeId) {
        itineraryPlaceRepository.deleteById(placeId);
    }
}
