package com.example.plan_voyage.dto;

import java.util.UUID;

public class AddListItemReqDto {
    private UUID listId;

    private String addedBy;

    private String listItem;

    public AddListItemReqDto(UUID listId, String addedBy, String listItem) {
        this.listId = listId;
        this.addedBy = addedBy;
        this.listItem = listItem;
    }

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
