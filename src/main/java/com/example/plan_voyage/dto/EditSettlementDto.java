package com.example.plan_voyage.dto;

import java.util.UUID;

public class EditSettlementDto {
    private UUID settlementId;

    private String payee;

    private String payer;

    private Double amount;

    public EditSettlementDto(UUID settlementId, String payee, String payer, Double amount) {
        this.settlementId = settlementId;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
    }

    public UUID getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(UUID settlementId) {
        this.settlementId = settlementId;
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
