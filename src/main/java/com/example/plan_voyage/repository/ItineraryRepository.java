package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Itinerary;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItineraryRepository extends JpaRepository<Itinerary, UUID> {
    List<Itinerary> findAllByTrip(Trip trip);
}
