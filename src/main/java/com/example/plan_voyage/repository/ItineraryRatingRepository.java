package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.ItineraryRating;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItineraryRatingRepository extends JpaRepository<ItineraryRating, UUID> {
    List<ItineraryRating> findAllByTrip(Trip trip);
}
