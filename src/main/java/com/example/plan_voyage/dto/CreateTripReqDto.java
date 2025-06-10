package com.example.plan_voyage.dto;

import java.util.Date;

public class CreateTripReqDto {
    private String destination;

    private Date startDate;

    private Date endDate;

    private String userId;

    public CreateTripReqDto(String destination, Date startDate, Date endDate, String userId) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
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
}
