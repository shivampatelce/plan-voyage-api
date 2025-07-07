package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Expense;
import com.example.plan_voyage.entity.Settlement;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    Budget setBudget(SetBudgetReqDto setBudgetReqDto);

    Budget getBudgetByTripId(UUID tripId);

    Budget updateBudget(UpdateBudgetDto updateBudgetDto);

    Expense addExpense(AddExpenseReqDto addExpenseReqDto);

    void deleteExpense(UUID expenseId);

    List<SettlementsResDto> calculateSettlements(String userId, UUID tripId);

    Settlement newSettlement(NewSettlementReqDto newSettlementReqDto);

    List<Settlement> settlementActivities(UUID tripId);

    void deleteSettlementActivity(UUID settlementId);

    Settlement editSettlementActivity(EditSettlementDto editSettlementDto);

    Expense editExpense(EditExpenseReqDto editExpenseReqDto);
}
