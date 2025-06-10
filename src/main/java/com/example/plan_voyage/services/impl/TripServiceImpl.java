package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.CreateTripReqDto;
import com.example.plan_voyage.dto.TripListResDto;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class TripServiceImpl implements TripService {

    @Value("${pixabay.api-key}")
    private String pixabayApiKey;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip createTrip(CreateTripReqDto createTripReqDto) {
        Trip trip = new Trip(createTripReqDto.getDestination(),
                            createTripReqDto.getStartDate(),
                            createTripReqDto.getEndDate(),
                            createTripReqDto.getUserId());
        trip = tripRepository.save(trip);
        return trip;
    }

    @Override
    public List<TripListResDto> getTripsByUserId(String userId) {
        List<TripListResDto> trips = new LinkedList<>();
        tripRepository.findByUserId(userId).stream().forEach((trip)->{
            try {
                trips.add(new TripListResDto(trip.getTripId(),
                        trip.getDestination(),
                        trip.getStartDate(),
                        trip.getEndDate(),
                        trip.getUserId(),
                        getDestinationImageLink(trip.getDestination())));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        return trips;
    }

    @Override
    public void removeTripById(UUID tripId) {
        tripRepository.deleteById(tripId);
    }

    private String getDestinationImageLink(String destination) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://pixabay.com/api/?key=" + pixabayApiKey + "&q=" + destination + "+place&image_type=photo";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject json = new JSONObject(response.getBody());
            String imageUrl = json
                    .getJSONArray("hits")
                    .getJSONObject(0)
                    .getString("largeImageURL");

            return imageUrl;
        }
        return "https://images.unsplash.com/photo-1494806812796-244fe51b774d?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8bW91bnRhaW5zfGVufDB8fDB8fHww";
    }

}
