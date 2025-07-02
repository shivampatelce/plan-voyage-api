package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.ToDoTask;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Budget findByTrip(Trip trip);
}
