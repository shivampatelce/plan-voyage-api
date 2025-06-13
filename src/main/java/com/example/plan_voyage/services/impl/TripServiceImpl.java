package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.CreateTripReqDto;
import com.example.plan_voyage.dto.InvitationListReqDto;
import com.example.plan_voyage.dto.InviteUserReqDto;
import com.example.plan_voyage.dto.TripResDto;
import com.example.plan_voyage.entity.InviteUserRequests;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.InviteUserRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TripServiceImpl implements TripService {

    @Value("${pixabay.api-key}")
    private String pixabayApiKey;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private InviteUserRepository inviteUserRepository;

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
    public List<TripResDto> getTripsByUserId(String userId) {
        List<TripResDto> trips = new LinkedList<>();
        tripRepository.findByUserId(userId).stream().forEach((trip) -> {
            try {
                trips.add(new TripResDto(trip.getTripId(),
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

    @Override
    public TripResDto getTripByTripId(UUID tripId) throws JSONException {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Invalid trip id: " + tripId));
        TripResDto tripResDto = new TripResDto(trip.getTripId(),
                trip.getDestination(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getUserId(),
                getDestinationImageLink(trip.getDestination()));
        return tripResDto;
    }

    @Override
    public boolean inviteUser(InviteUserReqDto inviteUserReqDto) {
        Trip trip = tripRepository.findById(inviteUserReqDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Invalid trip id: " + inviteUserReqDto.getTripId()));
        inviteUserReqDto.getEmails().stream().forEach(email -> {
            InviteUserRequests inviteUserRequests = new InviteUserRequests(email, trip, inviteUserReqDto.getSentAt());
            inviteUserRepository.save(inviteUserRequests);
        });
        return true;
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

    @Override
    public List<InviteUserRequests> getPendingInviteListByTripId(UUID tripId) {
        List<InviteUserRequests> pendingInviteRequests = inviteUserRepository.findAllByTripId(tripId);
        return pendingInviteRequests;
    }

    @Override
    public void deleteInvitation(UUID invitationId) {
        inviteUserRepository.deleteById(invitationId);
    }

    @Override
    public List<TripResDto> getTripInvitationsListByEmailId(String email) throws JSONException {
        List<InviteUserRequests> invitations = inviteUserRepository.findAllByEmailId(email);
        List<TripResDto> tripInvitations = new ArrayList<>();
        for (InviteUserRequests invitation : invitations) {
            try {
                Trip trip = invitation.getTripId();
                String imageLink = getDestinationImageLink(trip.getDestination());
                tripInvitations.add(
                        new TripResDto(trip.getTripId(), trip.getDestination(), trip.getStartDate(), trip.getEndDate(), trip.getUserId(), imageLink)
                );
            } catch (JSONException e) {
                System.err.println("Failed to fetch image link for destination: " + invitation.getTripId().getDestination());
            }
        }
        return tripInvitations;
    }

}
