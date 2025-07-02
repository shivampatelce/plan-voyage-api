package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Double totalBudget;

    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    public Budget() {
    }

    public Budget(Double totalBudget, Trip trip) {
        this.totalBudget = totalBudget;
        this.trip = trip;
    }

    public Budget(UUID id, Double totalBudget, Trip trip, List<Expense> expenses) {
        this.id = id;
        this.totalBudget = totalBudget;
        this.trip = trip;
        this.expenses = expenses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
