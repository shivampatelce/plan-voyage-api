package com.example.plan_voyage.dto;

import java.util.UUID;

public class NewSettlementReqDto {
    private UUID tripId;

    private String payee;

    private String payer;

    private Double amount;

    public NewSettlementReqDto(UUID tripId, String payee, String payer, Double amount) {
        this.tripId = tripId;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
