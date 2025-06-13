package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.InviteUserRequests;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InviteUserRepository extends JpaRepository<InviteUserRequests, UUID> {
    @Query("SELECT i FROM InviteUserRequests i WHERE i.tripId.tripId = :tripId")
    List<InviteUserRequests> findAllByTripId(@Param("tripId") UUID tripId);

    @Query("SELECT i FROM InviteUserRequests i WHERE LOWER(i.email) = LOWER(:email)")
    List<InviteUserRequests> findAllByEmailId(@Param("email") String email);

}
