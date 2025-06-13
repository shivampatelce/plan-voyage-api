package com.example.plan_voyage.dto;

public class InvitationListReqDto {
    private String email;

    public InvitationListReqDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
