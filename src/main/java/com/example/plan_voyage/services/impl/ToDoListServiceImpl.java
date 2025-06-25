package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddTaskReqDto;
import com.example.plan_voyage.dto.MarkAsDoneReqDto;
import com.example.plan_voyage.dto.UpdateTaskReqDto;
import com.example.plan_voyage.entity.ToDoTask;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.ToDoListRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public ToDoTask addTask(AddTaskReqDto addTaskReqDto) {
        Trip trip = tripRepository.findById(addTaskReqDto.getTripId()).orElseThrow(()-> new RuntimeException("Invalid Trip Id"));
        ToDoTask toDoTask = new ToDoTask(addTaskReqDto.getTaskTitle(), addTaskReqDto.getCreatedBy(), trip);
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
        return toDoListRepository.save(toDoTask);
    }

    @Override
    public ToDoTask markTaskAsDone(MarkAsDoneReqDto markAsDoneReqDto) {
        ToDoTask toDoTask = toDoListRepository.findById(markAsDoneReqDto.getTaskId())
                .orElseThrow(()-> new RuntimeException("Invalid task id"));
        toDoTask.setMarkedDoneBy(markAsDoneReqDto.getUserId());
        return toDoListRepository.save(toDoTask);
    }
}
