package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID expenseId;

    private Double amount;

    private String title;

    private String category;

    private Date date;

    private String paidBy;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    @JsonProperty("budget")
    @JsonIgnore
    private Budget budget;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SplitDetail> splitDetails = new ArrayList<>();

    public Expense() {
    }

    public Expense(Double amount, String title, String category, Date date, String paidBy, List<SplitDetail> splitDetails) {
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.splitDetails = splitDetails;
    }

    public Expense(Double amount, String title, String category, Date date, String paidBy, Budget budget) {
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.budget = budget;
    }

    public Expense(UUID expenseId, Double amount, String title, String category, Date date, String paidBy, List<SplitDetail> splitDetails) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.splitDetails = splitDetails;
    }

    public Expense(UUID expenseId, Double amount, String title, String category, Date date, String paidBy, Budget budget, List<SplitDetail> splitDetails) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.budget = budget;
        this.splitDetails = splitDetails;
    }

    public Expense(Double amount, String title, String category, Date date, String paidBy, Budget budget, List<SplitDetail> splitDetails) {
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
        this.budget = budget;
        this.splitDetails = splitDetails;
    }

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
        this.expenseId = expenseId;
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

    public List<SplitDetail> getSplitDetails() {
        return splitDetails;
    }

    public void setSplitDetails(List<SplitDetail> splitDetails) {
        this.splitDetails = splitDetails;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", paidBy='" + paidBy + '\'' +
                ", budget=" + budget +
                ", splitDetails=" + splitDetails +
                '}';
    }
}
