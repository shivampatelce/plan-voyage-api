package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.DocumentDetails;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentDetailsRepository extends JpaRepository<DocumentDetails, UUID> {
    List<DocumentDetails> findAllByTrip(Trip trip);
}
