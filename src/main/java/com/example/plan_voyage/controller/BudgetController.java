package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Expense;
import com.example.plan_voyage.entity.Settlement;
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

    @PutMapping("/edit-expense")
    public ResponseEntity<SuccessResponse<Expense>> editExpense(@RequestBody EditExpenseReqDto editExpenseReqDto) {
        Expense expense;
        try {
            expense = budgetService.editExpense(editExpenseReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/edit-expense");
        }
        return success(expense, "Expense has been updated for trip");
    }

    @DeleteMapping("/delete-expense/{expenseId}")
    public ResponseEntity<SuccessMessageResponse> deleteExpense(@PathVariable UUID expenseId) {
        budgetService.deleteExpense(expenseId);
        return success("Expense has been deleted successfully.");
    }

    @PostMapping("/get-settlements")
    public ResponseEntity<SuccessResponse<List<SettlementsResDto>>> getSettlements(@RequestBody GetSettlementsReqDto getSettlementsReqDto){
        List<SettlementsResDto> settlementList;
        try {
            settlementList = budgetService.calculateSettlements(getSettlementsReqDto.getUserId(), getSettlementsReqDto.getTripId());
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/get-settlements");
        }
        return success(settlementList, "List of settlements");
    }

    @PostMapping("/settlement")
    public ResponseEntity<SuccessResponse<Settlement>> newSettlement(@RequestBody NewSettlementReqDto newSettlementReqDto) {
        Settlement settlement;
        try {
            settlement = budgetService.newSettlement(newSettlementReqDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/settlements");
        }
        return success(settlement, "New settlement has been done.");
    }

    @GetMapping("/settlement-activities/{tripId}")
    public ResponseEntity<List<Settlement>> settlementActivities(@PathVariable UUID tripId) {
        List<Settlement> settlements;
        try {
            settlements = budgetService.settlementActivities(tripId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/settlement-activities/" + tripId);
        }
        return success(settlements, "Settlement activities list.");
    }

    @PutMapping("/edit-settlement-activity")
    public ResponseEntity<Settlement> editSettlement(@RequestBody EditSettlementDto editSettlementDto) {
        Settlement settlement;
        try {
            settlement = budgetService.editSettlementActivity(editSettlementDto);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/edit-settlement-activity");
        }
        return success(settlement, "Settlement activity has been edited.");
    }

    @DeleteMapping("/delete-settlement-activity/{settlementId}")
    public ResponseEntity<SuccessMessageResponse> deleteSettlement(@PathVariable UUID settlementId) {
        try {
            budgetService.deleteSettlementActivity(settlementId);
        } catch (Exception e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/budget/delete-settlement-activity/" + settlementId);
        }
        return success("Settlement has been deleted.");
    }
}
