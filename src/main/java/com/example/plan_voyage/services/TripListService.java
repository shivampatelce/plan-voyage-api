package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.AddListItemReqDto;
import com.example.plan_voyage.dto.CreateListReqDto;
import com.example.plan_voyage.entity.TripList;

import java.util.List;
import java.util.UUID;

public interface TripListService {
    TripList createTripList(CreateListReqDto createListReqDto);

    List<TripList> tripList(UUID tripId);

    TripList addListItem(AddListItemReqDto addListItemReqDto);

    void removeList(UUID listId);

    void removeListItem(UUID itemId);
}
