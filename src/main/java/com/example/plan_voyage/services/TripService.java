package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.InviteUserRequests;
import com.example.plan_voyage.entity.Trip;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;
import java.util.UUID;

public interface TripService {
    Trip createTrip(CreateTripReqDto createTripReqDto);

    List<TripResDto> getTripsByUserId(String userId);

    void removeTripById(UUID tripId);

    TripResDto getTripByTripId(UUID tripId) throws JSONException;

    boolean inviteUser(InviteUserReqDto inviteUserReqDto);

    List<InviteUserRequests> getPendingInviteListByTripId(UUID tripId);

    void deleteInvitation(UUID invitationId);

    List<InvitationListResDto> getTripInvitationsListByEmailId(String email) throws JSONException;

    TripResDto getInvitationDetailByInvitation(TripInvitationDetailReqDto tripInvitationDetailReqDto) throws JSONException;

    void joinTrip(JoinTripReqDto joinTripReqDto);

    void exitFromTrip(ExitTripReqDto exitTripReqDto);
}
