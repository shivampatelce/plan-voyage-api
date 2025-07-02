package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class SplitDetail {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID splitDetailId;

    private String userId;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonProperty("expense")
    @JsonIgnore
    private Expense expense;

    public SplitDetail() {
    }

    public SplitDetail(String userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public SplitDetail(String userId, Double amount, Expense expense) {
        this.userId = userId;
        this.amount = amount;
        this.expense = expense;
    }

    public SplitDetail(UUID splitDetailId, String userId, Double amount) {
        this.splitDetailId = splitDetailId;
        this.userId = userId;
        this.amount = amount;
    }

    public SplitDetail(UUID splitDetailId, String userId, Double amount, Expense expense) {
        this.splitDetailId = splitDetailId;
        this.userId = userId;
        this.amount = amount;
        this.expense = expense;
    }

    public UUID getSplitDetailId() {
        return splitDetailId;
    }

    public void setSplitDetailId(UUID splitDetailId) {
        this.splitDetailId = splitDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
