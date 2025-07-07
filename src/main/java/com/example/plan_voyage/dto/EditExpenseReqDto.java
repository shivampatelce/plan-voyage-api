package com.example.plan_voyage.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EditExpenseReqDto {
    private UUID expenseId;

    private Double amount;

    private String title;

    private String category;

    private Date date;

    private String paidBy;

    private List<EditSplitDetailDto> splitDetails;

    public EditExpenseReqDto(UUID expenseId, Double amount, String title, String category, Date date, String paidBy, List<EditSplitDetailDto> splitDetails) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
        this.paidBy = paidBy;
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

    public List<EditSplitDetailDto> getSplitDetails() {
        return splitDetails;
    }

    public void setSplitDetails(List<EditSplitDetailDto> splitDetails) {
        this.splitDetails = splitDetails;
    }
}
