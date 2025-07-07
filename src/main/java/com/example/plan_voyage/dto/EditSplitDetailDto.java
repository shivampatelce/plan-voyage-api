package com.example.plan_voyage.dto;

import java.util.UUID;

public class EditSplitDetailDto {
    private UUID splitDetailId;

    private String userId;

    private Double amount;

    public EditSplitDetailDto(UUID splitDetailId, String userId, Double amount) {
        this.splitDetailId = splitDetailId;
        this.userId = userId;
        this.amount = amount;
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
}
