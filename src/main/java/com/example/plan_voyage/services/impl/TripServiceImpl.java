package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.*;
import com.example.plan_voyage.repository.*;
import com.example.plan_voyage.services.KeycloakService;
import com.example.plan_voyage.services.TripService;
import jakarta.transaction.Transactional;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class TripServiceImpl implements TripService {

    @Value("${pixabay.api-key}")
    private String pixabayApiKey;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private InviteUserRepository inviteUserRepository;

    @Autowired
    private TripUsersRepository tripUsersRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private ItineraryRatingRepository itineraryRatingRepository;

    @Override
    public Trip createTrip(CreateTripReqDto createTripReqDto) {
        Trip trip = new Trip(createTripReqDto.getDestination(),
                createTripReqDto.getStartDate(),
                createTripReqDto.getEndDate(),
                createTripReqDto.getUserId());
        trip = tripRepository.save(trip);

        TripUsers tripUsers = new TripUsers(trip.getUserId(), trip);
        tripUsersRepository.save(tripUsers);

        return trip;
    }

    @Override
    public List<TripResDto> getTripsByUserId(String userId) {
        List<TripResDto> trips = new LinkedList<>();

        List<TripUsers> tripUsers = tripUsersRepository.findAllByUserId(userId);

        tripUsers.stream().forEach((tripUser) -> {
            Trip trip = tripUser.getTrip();

            List<UserDetailsDto> users = tripUsersRepository.findAllByTripId(trip.getTripId())
                    .stream().map(user -> getUserDetailsByUserId(user.getUserId())).toList();

            String imageLink = "";
            try {
                imageLink = getDestinationImageLink(trip.getDestination());
            } catch (JSONException e) {
                System.err.println("Failed to fetch image link for destination: " + trip.getDestination());
            }

            trips.add(new TripResDto(trip.getTripId(),
                    trip.getDestination(),
                    trip.getStartDate(),
                    trip.getEndDate(),
                    trip.getUserId(),
                    imageLink,
                    users
            ));
        });
        return trips;
    }

    @Override
    @Transactional
    public void removeTripById(UUID tripId) {
        tripUsersRepository.deleteAll(tripUsersRepository.findAllByTripId(tripId));
        tripRepository.deleteById(tripId);
    }

    @Override
    public TripResDto getTripByTripId(UUID tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Invalid trip id: " + tripId));

        List<UserDetailsDto> tripUsers = tripUsersRepository.findAllByTripId(trip.getTripId())
                .stream().map(user -> getUserDetailsByUserId(user.getUserId())).toList();

        String imageLink = "";
        try {
            imageLink = getDestinationImageLink(trip.getDestination());
        } catch (JSONException e) {
            System.err.println("Failed to fetch image link for destination: " + trip.getDestination());
        }

        return new TripResDto(trip.getTripId(),
                trip.getDestination(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getUserId(),
                imageLink,
                tripUsers);
    }

    private UserDetailsDto getUserDetailsByUserId(String userId) {
        UserRepresentation userDetails = keycloakService.getUserById(userId);
        return new UserDetailsDto(userId, userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail());
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
    public List<InvitationListResDto> getTripInvitationsListByEmailId(String email) throws JSONException {
        List<InviteUserRequests> invitations = inviteUserRepository.findAllByEmailId(email);
        List<InvitationListResDto> tripInvitations = new ArrayList<>();
        for (InviteUserRequests invitation : invitations) {
            Trip trip = invitation.getTripId();
            String imageLink = "";
            try {
                imageLink = getDestinationImageLink(trip.getDestination());
            } catch (JSONException e) {
                System.err.println("Failed to fetch image link for destination: " + invitation.getTripId().getDestination());
            }

            tripInvitations.add(
                    new InvitationListResDto(invitation.getInvitationId(),
                            new TripResDto(trip.getTripId(),
                                    trip.getDestination(),
                                    trip.getStartDate(),
                                    trip.getEndDate(),
                                    trip.getUserId(),
                                    imageLink)
                    )
            );
        }
        return tripInvitations;
    }

    @Override
    public TripResDto getInvitationDetailByInvitation(TripInvitationDetailReqDto tripInvitationDetailReqDto) {
        InviteUserRequests invitationRequest = inviteUserRepository.findByInvitationIdAndEmail(tripInvitationDetailReqDto.getInvitationId(), tripInvitationDetailReqDto.getEmail());
        TripResDto tripResDto;
        if (invitationRequest != null) {
            Trip trip = invitationRequest.getTripId();
            String imageLink = "";
            try {
                imageLink = getDestinationImageLink(trip.getDestination());
            } catch (JSONException e) {
                System.err.println("Failed to fetch image link for destination: " + trip.getDestination());
            }
            tripResDto = new TripResDto(trip.getTripId(), trip.getDestination(), trip.getStartDate(), trip.getEndDate(), trip.getUserId(), imageLink);
        } else {
            throw new RuntimeException("Invitation not found. It may have been deleted, or the associated trip plan might no longer exist.");
        }

        return tripResDto;
    }

    @Override
    public void joinTrip(JoinTripReqDto joinTripReqDto) {
        InviteUserRequests inviteRequest = inviteUserRepository.findById(joinTripReqDto.getInvitationId())
                .orElseThrow(() -> new RuntimeException("Invalid invitation id"));

        if (inviteRequest != null) {
            Trip trip = tripRepository.findById(joinTripReqDto.getTripId())
                    .orElseThrow(() -> new RuntimeException("Invalid trip id"));
            TripUsers tripUsers = new TripUsers(joinTripReqDto.getUserId(), trip);

            tripUsersRepository.save(tripUsers);

            inviteUserRepository.delete(inviteRequest);
        }
    }

    @Override
    public void exitFromTrip(ExitTripReqDto exitTripReqDto) {
        tripUsersRepository.deleteByTripIdAndUserId(exitTripReqDto.getTripId(), exitTripReqDto.getUserId());
    }

    @Override
    public List<TripResDto> relatedTripList(String placeName, UUID tripId) {
        List<Trip> trips = tripRepository.findByDestinationContainingIgnoreCase(placeName).stream()
                .filter((trip)-> {
                    if(!trip.getTripId().equals(tripId)) {
                        List<Itinerary> itineraries = itineraryRepository.findAllByTrip(trip);
                        for(Itinerary itinerary: itineraries) {
                            if(!itinerary.getItineraryPlaces().isEmpty()) {
                                return true;
                            }
                        }
                    }
                    return false;
                }).toList();

        List<TripResDto> tripResDtoList = new ArrayList<>();

        trips.stream().forEach(trip -> {
            String imageLink = "";
            try {
                imageLink = getDestinationImageLink(trip.getDestination());
            } catch (JSONException e) {
                System.err.println("Failed to fetch image link for destination: " + trip.getDestination());
            }

            UserRepresentation userInfo = keycloakService.getUserById(trip.getUserId());

            tripResDtoList.add(new TripResDto(trip.getTripId(),
                    trip.getDestination(),
                    trip.getStartDate(),
                    trip.getEndDate(),
                    trip.getUserId(),
                    imageLink,
                    calculateRating(trip),
                    userInfo.getFirstName() + " " + userInfo.getLastName()
                    ));
        });
        return tripResDtoList;
    }

    @Override
    public Trip updateTripDates(UpdateTripDatesDto updateTripDatesDto) {
        Trip trip = tripRepository.findById(updateTripDatesDto.getTripId())
                .orElseThrow(()-> new RuntimeException("Invalid trip id"));

        trip.setStartDate(updateTripDatesDto.getStartDate());
        trip.setEndDate(updateTripDatesDto.getEndDate());

        return tripRepository.save(trip);
    }

    private double calculateRating(Trip trip) {
        List<ItineraryRating> itineraryRatings = itineraryRatingRepository.findAllByTrip(trip);

        if (itineraryRatings == null || itineraryRatings.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (ItineraryRating rating : itineraryRatings) {
            total += rating.getRating();
        }

        return total / itineraryRatings.size();
    }

}
