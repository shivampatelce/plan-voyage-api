package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoordinatesRepository extends JpaRepository<Coordinates, UUID> {
}
