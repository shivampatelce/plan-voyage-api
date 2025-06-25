package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.entity.TripList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TripListRepository extends JpaRepository<TripList, UUID> {
    List<TripList> findAllByTrip(Trip trip);
}
