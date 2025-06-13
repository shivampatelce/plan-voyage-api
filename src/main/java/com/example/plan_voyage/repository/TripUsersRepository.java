package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.InviteUserRequests;
import com.example.plan_voyage.entity.TripUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TripUsersRepository extends JpaRepository<TripUsers, UUID> {
    @Query("SELECT i FROM TripUsers i WHERE i.trip.tripId = :tripId")
    List<TripUsers> findAllByTripId(@Param("tripId") UUID tripId);

    List<TripUsers> findAllByUserId(String userId);
}
