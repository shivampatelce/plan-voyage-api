package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Trip {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID tripId;

    private String destination;

    private Date startDate;

    private Date endDate;

    @JsonProperty("creatorId")
    private String userId;

    @OneToMany(mappedBy = "tripId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<InviteUserRequests> invitations = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripUsers> tripUsers = new ArrayList<>();

    @OneToMany(mappedBy = "taskId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ToDoTask> toDoTasks = new ArrayList<>();

    @OneToMany(mappedBy = "tripListId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TripList> tripLists = new ArrayList<>();

    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Budget budget;

    @OneToMany(mappedBy = "itineraryId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Itinerary> itineraries = new ArrayList<>();

    public Trip() {}

    public Trip(String destination, Date startDate, Date endDate, String userId) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<TripUsers> tripUsers) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.tripUsers = tripUsers;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<InviteUserRequests> invitations, List<TripUsers> tripUsers) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.invitations = invitations;
        this.tripUsers = tripUsers;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<InviteUserRequests> invitations, List<TripUsers> tripUsers, List<ToDoTask> toDoTasks) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.invitations = invitations;
        this.tripUsers = tripUsers;
        this.toDoTasks = toDoTasks;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<InviteUserRequests> invitations, List<TripUsers> tripUsers, List<ToDoTask> toDoTasks, List<TripList> tripLists) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.invitations = invitations;
        this.tripUsers = tripUsers;
        this.toDoTasks = toDoTasks;
        this.tripLists = tripLists;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<InviteUserRequests> invitations, List<TripUsers> tripUsers, List<ToDoTask> toDoTasks, List<TripList> tripLists, Budget budget) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.invitations = invitations;
        this.tripUsers = tripUsers;
        this.toDoTasks = toDoTasks;
        this.tripLists = tripLists;
        this.budget = budget;
    }

    public Trip(UUID tripId, String destination, Date startDate, Date endDate, String userId, List<InviteUserRequests> invitations, List<TripUsers> tripUsers, List<ToDoTask> toDoTasks, List<TripList> tripLists, Budget budget, List<Itinerary> itineraries) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.invitations = invitations;
        this.tripUsers = tripUsers;
        this.toDoTasks = toDoTasks;
        this.tripLists = tripLists;
        this.budget = budget;
        this.itineraries = itineraries;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<InviteUserRequests> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<InviteUserRequests> invitations) {
        this.invitations = invitations;
    }

    public List<TripUsers> getTripUsers() {
        return tripUsers;
    }

    public void setTripUsers(List<TripUsers> tripUsers) {
        this.tripUsers = tripUsers;
    }

    public List<ToDoTask> getToDoTasks() {
        return toDoTasks;
    }

    public void setToDoTasks(List<ToDoTask> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    public List<TripList> getTripLists() {
        return tripLists;
    }

    public void setTripLists(List<TripList> tripLists) {
        this.tripLists = tripLists;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
}
