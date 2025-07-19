package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.InviteUserRequests;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.services.TripService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.ErrorCode;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
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
        return success(trip, "New trip has been created.");
    }

    @PostMapping("/trip-list")
    public ResponseEntity<SuccessResponse<List<TripResDto>>> trips(@RequestBody TripListDto tripListDto) {
        List<TripResDto> trips = tripService.getTripsByUserId(tripListDto.getUserId());
        return success(trips, "List of trips");
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<SuccessResponse<TripResDto>> tripOverView(@PathVariable UUID tripId) {
        TripResDto trip = null;
        try {
            trip = tripService.getTripByTripId(tripId);
        } catch (JSONException e) {
            return error("Something goes wrong while fetching destination image", HttpStatus.INTERNAL_SERVER_ERROR, "/trip");
        }
        return success(trip, "Trip overview");
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<SuccessMessageResponse> removeTrip(@PathVariable UUID tripId) {
        tripService.removeTripById(tripId);
        return success("Trip removed with id: " + tripId);
    }

    @PostMapping("/invite-user")
    public ResponseEntity<SuccessMessageResponse> inviteUser(@RequestBody InviteUserReqDto inviteUserReqDto) {
        boolean isInviteSent = tripService.inviteUser(inviteUserReqDto);
        if (!isInviteSent) {
            return error("Something goes wrong while sending invitation", HttpStatus.BAD_REQUEST, "/invite-user");
        }
        return success("Invitation has been sent");
    }

    @PostMapping("/pending-invite/{tripId}")
    public ResponseEntity<List<InviteUserRequests>> inviteUsersList(@PathVariable UUID tripId) {
        List<InviteUserRequests> pendingRequests = tripService.getPendingInviteListByTripId(tripId);
        return success(pendingRequests, "List of pending invited users");
    }

    @DeleteMapping("/delete-invitation/{invitationId}")
    public ResponseEntity<SuccessMessageResponse> deleteInvite(@PathVariable UUID invitationId) {
        tripService.deleteInvitation(invitationId);
        return success("Invitation has been deleted with id: " + invitationId);
    }

    @PostMapping("/invitations")
    public ResponseEntity<List<InvitationListResDto>> getInvitationsByEmailId(@RequestBody InvitationListReqDto invitationListReqDto) {
        List<InvitationListResDto> invitations = null;
        try {
            invitations = tripService.getTripInvitationsListByEmailId(invitationListReqDto.getEmail());
        } catch (JSONException e) {
            return error("Something goes wrong while fetching destination image", HttpStatus.INTERNAL_SERVER_ERROR, "/invitations");
        }
        return success(invitations, "List of invitations");
    }

    @PostMapping("/invitation")
    public ResponseEntity<TripResDto> getInvitationDetailsByInvitationId(@RequestBody TripInvitationDetailReqDto tripInvitationDetailReqDto){
        TripResDto tripDetails = null;
        try {
            tripDetails = tripService.getInvitationDetailByInvitation(tripInvitationDetailReqDto);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, ErrorCode.INVALID_TRIP_INVITATION, "/invitation");
        }
        return success(tripDetails, "Trip details");
    }

    @PostMapping("/join-trip")
    public ResponseEntity<SuccessMessageResponse> joinTrip(@RequestBody JoinTripReqDto joinTripReqDto) {
        try {
            tripService.joinTrip(joinTripReqDto);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/invitation");
        }
        return success("User has been added to trip planning");
    }

    @DeleteMapping("/exit-trip")
    public ResponseEntity<SuccessMessageResponse> exitTrip(@RequestBody ExitTripReqDto exitTripReqDto) {
        tripService.exitFromTrip(exitTripReqDto);
        return success("User has been exited from trip planning.");
    }

    @PutMapping("/update-trip-dates")
    public ResponseEntity<SuccessResponse<Trip>> updateDate(@RequestBody UpdateTripDatesDto updateTripDatesDto) {
        Trip trip;
        try {
            trip = tripService.updateTripDates(updateTripDatesDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/v1/trip/update-trip-dates");
        }
        return success(trip,"Trip dates has been updated.");
    }
}
