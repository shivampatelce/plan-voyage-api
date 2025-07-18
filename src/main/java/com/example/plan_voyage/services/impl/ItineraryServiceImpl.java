package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddItineraryRatingReqDto;
import com.example.plan_voyage.dto.AddItineraryReqDto;
import com.example.plan_voyage.dto.AddPlaceNoteReqDto;
import com.example.plan_voyage.entity.*;
import com.example.plan_voyage.repository.*;
import com.example.plan_voyage.services.ItineraryService;
import com.example.plan_voyage.services.KeycloakService;
import com.example.plan_voyage.services.NotificationService;
import com.example.plan_voyage.util.NotificationActionUrl;
import com.example.plan_voyage.util.NotificationType;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    private ItineraryRatingRepository itineraryRatingRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TripUsersRepository tripUsersRepository;

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

        UserRepresentation userRepresentation = keycloakService.getUserById(addItineraryReqDto.getUserId());

        List<String> tripUsers = tripUsersRepository.findAllByTripId(trip.getTripId())
                .stream()
                .map((TripUsers::getUserId))
                .filter(userId -> !userId.equals(addItineraryReqDto.getUserId())).toList();

        notificationService.sendNotification(new Notification("Itinerary",
                userRepresentation.getFirstName() + " " + userRepresentation.getLastName() + " has added a new item to the itinerary",
                NotificationType.ITINERARY,
                NotificationActionUrl.ITINERARY.replace("{tripId}",trip.getTripId().toString()),
                LocalDateTime.now(),
                trip.getTripId()), tripUsers);

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

    @Override
    public ItineraryPlace addPlaceNote(AddPlaceNoteReqDto addPlaceNoteReqDto) {
        ItineraryPlace itineraryPlace = itineraryPlaceRepository.findById(addPlaceNoteReqDto.getItineraryPlaceId())
                .orElseThrow(() -> new RuntimeException("Invalid id: " + addPlaceNoteReqDto.getItineraryPlaceId()));

        itineraryPlace.setNotes(addPlaceNoteReqDto.getNotes());

        return itineraryPlaceRepository.save(itineraryPlace);
    }

    @Override
    public ItineraryRating addItineraryRating(AddItineraryRatingReqDto addItineraryRatingReqDto) {
        if(addItineraryRatingReqDto.getItineraryRatingId() == null) {
            Trip trip = tripRepository.findById(addItineraryRatingReqDto.getTripId())
                    .orElseThrow(() -> new RuntimeException("Invalid trip id"));

            UserRepresentation userInfo = keycloakService.getUserById(addItineraryRatingReqDto.getUserId());

            return itineraryRatingRepository.save(
                    new ItineraryRating(addItineraryRatingReqDto.getRating(),
                            addItineraryRatingReqDto.getComment(),
                            addItineraryRatingReqDto.getUserId(),
                            userInfo.getFirstName() + " " + userInfo.getLastName(),
                            trip)
            );
        } else {
            ItineraryRating itineraryRating = itineraryRatingRepository.findById(addItineraryRatingReqDto.getItineraryRatingId())
                                                                        .orElseThrow(()-> new RuntimeException("Invalid rating id"));

            itineraryRating.setComment(addItineraryRatingReqDto.getComment());
            itineraryRating.setRating(addItineraryRatingReqDto.getRating());

            return itineraryRatingRepository.save(itineraryRating);
        }
    }

    @Override
    public List<ItineraryRating> getItineraryRatingList(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new RuntimeException("Invalid trip id"));

        return itineraryRatingRepository.findAllByTrip(trip);
    }

}
