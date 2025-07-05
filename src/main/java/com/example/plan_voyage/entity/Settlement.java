package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Settlement {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID settlementId;

    private String payer;

    private String payee;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    @JsonProperty("budget")
    @JsonIgnore
    private Budget budget;

    public Settlement() {
    }

    public Settlement(String payer, String payee, Double amount, Budget budget) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.budget = budget;
    }

    public Settlement(UUID settlementId, String payer, String payee, Double amount, Budget budget) {
        this.settlementId = settlementId;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.budget = budget;
    }

    public UUID getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(UUID settlementId) {
        this.settlementId = settlementId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
