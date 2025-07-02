package com.example.plan_voyage.dto;

import com.example.plan_voyage.entity.SplitDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddExpenseReqDto {
    private UUID tripId;

    private Double amount;

    private String title;

    private String category;

    private Date date;

    private String paidBy;

    private List<SplitDetailDto> splitDetails = new ArrayList<>();

    public AddExpenseReqDto(UUID tripId, Double amount, String title, String category, Date date, String paidBy, List<SplitDetailDto> splitDetails) {
        this.tripId = tripId;
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.splitDetails = splitDetails;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public List<SplitDetailDto> getSplitDetails() {
        return splitDetails;
    }

    public void setSplitDetails(List<SplitDetailDto> splitDetails) {
        this.splitDetails = splitDetails;
    }

    @Override
    public String toString() {
        return "AddExpenseReqDto{" +
                "tripId=" + tripId +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", paidBy='" + paidBy + '\'' +
                ", splitDetails=" + splitDetails +
                '}';
    }
}
