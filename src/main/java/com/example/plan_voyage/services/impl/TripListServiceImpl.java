package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddListItemReqDto;
import com.example.plan_voyage.dto.CreateListReqDto;
import com.example.plan_voyage.entity.*;
import com.example.plan_voyage.repository.ListItemRepository;
import com.example.plan_voyage.repository.TripListRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.repository.TripUsersRepository;
import com.example.plan_voyage.services.KeycloakService;
import com.example.plan_voyage.services.NotificationService;
import com.example.plan_voyage.services.TripListService;
import com.example.plan_voyage.util.NotificationActionUrl;
import com.example.plan_voyage.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripListServiceImpl implements TripListService {

    @Autowired
    private TripListRepository tripListRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private TripUsersRepository tripUsersRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public TripList createTripList(CreateListReqDto createListReqDto) {
        Trip trip = tripRepository.findById(createListReqDto.getTripId())
                .orElseThrow(()->new RuntimeException("Invalid trip id"));
        TripList tripList = new TripList(createListReqDto.getListTitle(), createListReqDto.getUserId(), new ArrayList<>(), trip);
        sendTripListNotification(createListReqDto.getTripId(), createListReqDto.getUserId(), "Your trip list has been updated by another trip member.");
        return tripListRepository.save(tripList);
    }

    @Override
    public List<TripList> tripList(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new RuntimeException("Invalid trip id"));
        return tripListRepository.findAllByTrip(trip);
    }

    @Override
    public TripList addListItem(AddListItemReqDto addListItemReqDto) {
        TripList tripList = tripListRepository.findById(addListItemReqDto.getListId())
                .orElseThrow(()-> new RuntimeException("Invalid list id"));

        ListItem listItem = new ListItem(addListItemReqDto.getListItem(), addListItemReqDto.getAddedBy(), tripList);

        listItemRepository.save(listItem);

        return tripListRepository.findById(addListItemReqDto.getListId()).get();
    }

    @Override
    public void removeList(UUID listId) {
        tripListRepository.deleteById(listId);
    }

    @Override
    public void removeListItem(UUID itemId) {
        listItemRepository.deleteById(itemId);
    }

    private void sendTripListNotification(UUID tripId, String taskPerformerId, String message) {
        List<String> tripUsers = tripUsersRepository.findAllByTripId(tripId)
                .stream()
                .map((TripUsers::getUserId))
                .filter(userId -> !userId.equals(taskPerformerId)).toList();

        notificationService.sendNotification(new Notification("Trip List",
                message,
                NotificationType.TRIP_LIST,
                NotificationActionUrl.TRIP_LIST.replace("{tripId}",tripId.toString()),
                LocalDateTime.now(),
                tripId), tripUsers);
    }
}
