package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.AddTaskReqDto;
import com.example.plan_voyage.dto.MarkAsDoneReqDto;
import com.example.plan_voyage.dto.UpdateTaskReqDto;
import com.example.plan_voyage.entity.ToDoTask;
import com.example.plan_voyage.services.ToDoListService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/v1/to-do-list")
public class ToDoListController extends BaseController {

    @Autowired
    private ToDoListService toDoListService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<ToDoTask>> addTask(@RequestBody AddTaskReqDto addTaskReqDto) {
        ToDoTask toDoTask;
        try {
            toDoTask = toDoListService.addTask(addTaskReqDto);
        } catch (RuntimeException exception) {
            return error(exception.getMessage(), HttpStatus.BAD_REQUEST, "/v1/to-do-list/add");
        }
        return success(toDoTask,"New task add to to-do list");
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<SuccessResponse<List<ToDoTask>>> getToDoList(@PathVariable UUID tripId) {
        List<ToDoTask> toDoList;
        try {
            toDoList = toDoListService.getToDoList(tripId);
        } catch (RuntimeException exception) {
            return error(exception.getMessage(), HttpStatus.BAD_REQUEST, "/v1/to-do-list");
        }
        return success(toDoList, "List of to-do list");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<SuccessMessageResponse> deleteTask(@PathVariable UUID taskId) {
        toDoListService.deleteTask(taskId);
        return success("Task deleted");
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<ToDoTask>> updateTask(@RequestBody UpdateTaskReqDto updateTaskReqDto) {
        return success(toDoListService.updateTask(updateTaskReqDto), "Task title updated");
    }

    @PostMapping("/mark-as-done")
    public ResponseEntity<SuccessResponse<ToDoTask>> markAsDone(@RequestBody MarkAsDoneReqDto markAsDoneReqDto) {
        return success(toDoListService.markTaskAsDone(markAsDoneReqDto), "Marked task as done.");
    }
}
