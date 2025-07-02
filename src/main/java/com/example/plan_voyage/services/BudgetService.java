package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.AddExpenseReqDto;
import com.example.plan_voyage.dto.SetBudgetReqDto;
import com.example.plan_voyage.dto.UpdateBudgetDto;
import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Expense;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    Budget setBudget(SetBudgetReqDto setBudgetReqDto);

    Budget getBudgetByTripId(UUID tripId);

    Budget updateBudget(UpdateBudgetDto updateBudgetDto);

    Expense addExpense(AddExpenseReqDto addExpenseReqDto);

    void deleteExpense(UUID expenseId);
}
