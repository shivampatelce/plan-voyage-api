package com.example.plan_voyage.controller;

import com.example.plan_voyage.entity.NotificationReceiver;
import com.example.plan_voyage.services.NotificationService;
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
@RequestMapping("/v1/notification")
public class NotificationController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<List<NotificationReceiver>>> getNotificationsById(@PathVariable String userId) {
        List<NotificationReceiver> notificationList;
        try {
            notificationList = notificationService.getNotificationsByUserId(userId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/v1/notification/"+userId);
        }
        return success(notificationList, "List of notifications");
    }

    @PostMapping("/mark-as-seen/{notificationId}/{userId}")
    public ResponseEntity<SuccessMessageResponse> markNotificationSeen(@PathVariable UUID notificationId, @PathVariable String userId) {
        try {
            notificationService.markNotificationSeen(notificationId, userId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/v1/notification/mark-as-seen/"+notificationId + "/" +userId);
        }
        return success("Notification marked as seen");
    }

}
