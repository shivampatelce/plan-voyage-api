package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    List<Trip> findByUserId(String userId);

    List<Trip> findByDestinationContainingIgnoreCase(String placeName);

}
