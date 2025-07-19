package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddTaskReqDto;
import com.example.plan_voyage.dto.MarkAsDoneReqDto;
import com.example.plan_voyage.dto.UpdateTaskReqDto;
import com.example.plan_voyage.entity.Notification;
import com.example.plan_voyage.entity.ToDoTask;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.entity.TripUsers;
import com.example.plan_voyage.repository.ToDoListRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.repository.TripUsersRepository;
import com.example.plan_voyage.services.KeycloakService;
import com.example.plan_voyage.services.NotificationService;
import com.example.plan_voyage.services.ToDoListService;
import com.example.plan_voyage.util.NotificationActionUrl;
import com.example.plan_voyage.util.NotificationType;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private TripUsersRepository tripUsersRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public ToDoTask addTask(AddTaskReqDto addTaskReqDto) {
        Trip trip = tripRepository.findById(addTaskReqDto.getTripId()).orElseThrow(()-> new RuntimeException("Invalid Trip Id"));
        ToDoTask toDoTask = new ToDoTask(addTaskReqDto.getTaskTitle(), addTaskReqDto.getCreatedBy(), trip);
        sendToDoListNotification(addTaskReqDto.getTripId(), addTaskReqDto.getCreatedBy(), "Your to-do list has been updated by another trip member.");
        return toDoListRepository.save(toDoTask);
    }

    @Override
    public List<ToDoTask> getToDoList(UUID tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new RuntimeException("Invalid Trip Id"));
        return toDoListRepository.findAllByTrip(trip);
    }

    @Override
    public void deleteTask(UUID taskId) {
        toDoListRepository.deleteById(taskId);
    }

    @Override
    public ToDoTask updateTask(UpdateTaskReqDto updateTaskReqDto) {
        ToDoTask toDoTask = toDoListRepository.findById(updateTaskReqDto.getTaskId())
                .orElseThrow(()-> new RuntimeException("Invalid task id"));
        toDoTask.setTaskTitle(updateTaskReqDto.getTaskTitle());
        sendToDoListNotification(updateTaskReqDto.getTripId(), updateTaskReqDto.getUserId(), "Your to-do list has been updated by another trip member.");
        return toDoListRepository.save(toDoTask);
    }

    @Override
    public ToDoTask markTaskAsDone(MarkAsDoneReqDto markAsDoneReqDto) {
        ToDoTask toDoTask = toDoListRepository.findById(markAsDoneReqDto.getTaskId())
                .orElseThrow(()-> new RuntimeException("Invalid task id"));
        toDoTask.setMarkedDoneBy(markAsDoneReqDto.getUserId());
        sendToDoListNotification(markAsDoneReqDto.getTripId(), markAsDoneReqDto.getUserId(), "Your to-do list has been updated by another trip member.");
        return toDoListRepository.save(toDoTask);
    }

    private void sendToDoListNotification(UUID tripId, String taskPerformerId, String message) {
        List<String> tripUsers = tripUsersRepository.findAllByTripId(tripId)
                .stream()
                .map((TripUsers::getUserId))
                .filter(userId -> !userId.equals(taskPerformerId)).toList();

        notificationService.sendNotification(new Notification("To-Do List",
                message,
                NotificationType.TO_DO_LIST,
                NotificationActionUrl.TO_DO_LIST.replace("{tripId}",tripId.toString()),
                LocalDateTime.now(),
                tripId), tripUsers);
    }
}
