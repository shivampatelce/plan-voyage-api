package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddItineraryReqDto;
import com.example.plan_voyage.entity.Coordinates;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.ItineraryPlace;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.CoordinatesRepository;
import com.example.plan_voyage.repository.ItineraryPlaceRepository;
import com.example.plan_voyage.repository.ItineraryRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${opencage.api.key}")
    private String opencageApiKey;

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

            Coordinates coordinates = getCoordinates(newItineraryPlace);

            newItineraryPlace.setCoordinates(coordinates);

            itinerary.getItineraryPlaces().add(newItineraryPlace);
        } else {
            itinerary = itineraryRepository.save(new Itinerary(addItineraryReqDto.getItineraryId(), addItineraryReqDto.getDate(), trip));

            ItineraryPlace newItineraryPlace = itineraryPlaceRepository.save(new ItineraryPlace(addItineraryReqDto.getPlace(),
                                                                addItineraryReqDto.getCategory(),
                                                                addItineraryReqDto.getTime(),
                                                                itinerary
                                                            ));

            Coordinates coordinates = getCoordinates(newItineraryPlace);

            newItineraryPlace.setCoordinates(coordinates);

            itinerary.setItineraryPlaces(List.of(newItineraryPlace));
        }
        return itinerary;
    }

    private Coordinates getCoordinates(ItineraryPlace itineraryPlace) {
        String placeName = itineraryPlace.getPlace();
        String url = String.format("https://api.opencagedata.com/geocode/v1/json?q=%s&key=%s",
                URLEncoder.encode(placeName, StandardCharsets.UTF_8),
                opencageApiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Coordinates coordinates = null;

        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                JSONObject json = new JSONObject(response.getBody());
                if (json != null) {
                    String lat = String.valueOf(json.getJSONArray("results")
                            .getJSONObject(0)
                            .getJSONObject("geometry")
                            .getDouble("lat"));

                    String lng = String.valueOf(json.getJSONArray("results")
                            .getJSONObject(0)
                            .getJSONObject("geometry")
                            .getDouble("lng"));

                    coordinates = new Coordinates(lat, lng, itineraryPlace);
                }
            }
        } catch (JSONException exception) {
            System.out.println(exception.getMessage());
        }

        if(coordinates == null) {
            coordinates = new Coordinates(null, null, itineraryPlace);
        }

        return coordinatesRepository.save(coordinates);
    }

    @Override
    public void removePlace(UUID placeId) {
        itineraryPlaceRepository.deleteById(placeId);
    }

}
