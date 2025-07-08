package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.ItineraryPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItineraryPlaceRepository extends JpaRepository<ItineraryPlace, UUID> {
}
