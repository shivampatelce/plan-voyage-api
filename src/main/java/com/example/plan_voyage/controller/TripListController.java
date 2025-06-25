package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.AddListItemReqDto;
import com.example.plan_voyage.dto.CreateListReqDto;
import com.example.plan_voyage.entity.TripList;
import com.example.plan_voyage.services.TripListService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/trip-list")
public class TripListController extends BaseController {

    @Autowired
    private TripListService tripListService;

    @GetMapping("/{tripId}")
    public ResponseEntity<List<TripList>> getTripList(@PathVariable UUID tripId) {
        List<TripList> tripList;
        try {
            tripList = tripListService.tripList(tripId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/trip-list/"+tripId);
        }
        return success(tripList, "Lists of trip");
    }

    @PostMapping
    public ResponseEntity<TripList> createTripList(@RequestBody CreateListReqDto createListReqDto) {
        TripList tripList;
        try {
            tripList = tripListService.createTripList(createListReqDto);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/trip-list");
        }
        return success(tripList, "New list has been created");
    }

    @PostMapping("/add-item")
    public ResponseEntity<TripList> addItem(@RequestBody AddListItemReqDto addListItemReqDto) {
        TripList tripList;
        try{
            tripList = tripListService.addListItem(addListItemReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/trip-list/add-item");
        }
        return success(tripList, "Item has been added to list.");
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<SuccessMessageResponse> removeList(@PathVariable UUID listId) {
        tripListService.removeList(listId);
        return success("List has been removed");
    }

    @DeleteMapping("/delete-item/{itemId}")
    public ResponseEntity<SuccessMessageResponse> removeListItem(@PathVariable UUID itemId) {
        tripListService.removeListItem(itemId);
        return success("List item has been removed");
    }
}
