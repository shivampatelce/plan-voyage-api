package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.AddTaskReqDto;
import com.example.plan_voyage.dto.MarkAsDoneReqDto;
import com.example.plan_voyage.dto.UpdateTaskReqDto;
import com.example.plan_voyage.entity.ToDoTask;

import java.util.List;
import java.util.UUID;

public interface ToDoListService {
    ToDoTask addTask(AddTaskReqDto addTaskReqDto);

    List<ToDoTask> getToDoList(UUID tripId);

    void deleteTask(UUID taskId);

    ToDoTask updateTask(UpdateTaskReqDto updateTaskReqDto);

    ToDoTask markTaskAsDone(MarkAsDoneReqDto markAsDoneReqDto);
}
