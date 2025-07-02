package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.AddExpenseReqDto;
import com.example.plan_voyage.dto.SetBudgetReqDto;
import com.example.plan_voyage.dto.UpdateBudgetDto;
import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Expense;
import com.example.plan_voyage.services.BudgetService;
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
@RequestMapping("/v1/budget")
public class BudgetController extends BaseController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/set-budget")
    public ResponseEntity<SuccessResponse<Budget>> setBudget(@RequestBody SetBudgetReqDto setBudgetReqDto) {
        Budget budget;
        try {
            budget = budgetService.setBudget(setBudgetReqDto);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/set-budget");
        }
        return success(budget, "Budget has been set for trip");
    }

    @PostMapping("/update-budget")
    public ResponseEntity<SuccessResponse<Budget>> updateBudget(@RequestBody UpdateBudgetDto updateBudgetDto) {
        Budget budget;
        try {
            budget = budgetService.updateBudget(updateBudgetDto);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/update-budget");
        }
        return success(budget, "Budget has been updated for trip");
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<SuccessResponse<Budget>> getBudget(@PathVariable UUID tripId) {
        Budget budget;
        try {
            budget = budgetService.getBudgetByTripId(tripId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/"+tripId);
        }
        return success(budget, "Budget of given trip-id");
    }

    @PostMapping("/add-expense")
    public ResponseEntity<SuccessResponse<Expense>> addExpense(@RequestBody AddExpenseReqDto addExpenseReqDto) {
        Expense expense;
        try {
            expense = budgetService.addExpense(addExpenseReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/add-expense");
        }
        return success(expense, "Expense has been added for trip");
    }

    @DeleteMapping("/delete-expense/{expenseId}")
    public ResponseEntity<SuccessMessageResponse> deleteExpense(@PathVariable UUID expenseId) {
        budgetService.deleteExpense(expenseId);
        return success("Expense has been deleted successfully.");
    }

}
