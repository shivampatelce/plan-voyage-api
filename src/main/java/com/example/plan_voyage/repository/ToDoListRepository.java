package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.ToDoTask;
import com.example.plan_voyage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToDoListRepository extends JpaRepository<ToDoTask, UUID> {
    List<ToDoTask> findAllByTrip(Trip trip);
}
