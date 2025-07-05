package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddExpenseReqDto;
import com.example.plan_voyage.dto.SetBudgetReqDto;
import com.example.plan_voyage.dto.SettlementsResDto;
import com.example.plan_voyage.dto.UpdateBudgetDto;
import com.example.plan_voyage.entity.Budget;
import com.example.plan_voyage.entity.Expense;
import com.example.plan_voyage.entity.SplitDetail;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.BudgetRepository;
import com.example.plan_voyage.repository.ExpenseRepository;
import com.example.plan_voyage.repository.SplitDetailRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private SplitDetailRepository splitDetailRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Budget setBudget(SetBudgetReqDto setBudgetReqDto) {
        Trip trip = tripRepository.findById(setBudgetReqDto.getTripId())
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));
        Budget budget = new Budget(setBudgetReqDto.getBudget(), trip);
        return budgetRepository.save(budget);
    }

    @Override
    public Budget getBudgetByTripId(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));
        return budgetRepository.findByTrip(trip);
    }

    @Override
    public Budget updateBudget(UpdateBudgetDto updateBudgetDto) {
        Trip trip = tripRepository.findById(updateBudgetDto.getTripId())
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));

        Budget budget = budgetRepository.findByTrip(trip);

        budget.setTotalBudget(updateBudgetDto.getBudget());

        return budgetRepository.save(budget);
    }

    @Override
    public Expense addExpense(AddExpenseReqDto addExpenseReqDto) {
        Trip trip = tripRepository.findById(addExpenseReqDto.getTripId())
                .orElseThrow(()-> new RuntimeException("Invalid Trip Id"));

        Budget budget = budgetRepository.findByTrip(trip);

        Expense expense = new Expense(addExpenseReqDto.getAmount(),
                addExpenseReqDto.getTitle(),
                addExpenseReqDto.getCategory(),
                addExpenseReqDto.getDate(),
                addExpenseReqDto.getPaidBy(),
                budget);

        Expense addedExpense = expenseRepository.save(expense);

        addExpenseReqDto.getSplitDetails().forEach(splitDetail -> {
            SplitDetail newAddedSplitDetail = new SplitDetail(splitDetail.getUserId(), splitDetail.getAmount(), addedExpense);
            splitDetailRepository.save(newAddedSplitDetail);
        });

        return expense;
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public List<SettlementsResDto> calculateSettlements(String userId, UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Invalid Trip Id"));

        Budget budget = budgetRepository.findByTrip(trip);
        List<Expense> expenses = budget.getExpenses();

        Map<String, Double> settlementMap = new HashMap<>();

        for (Expense expense : expenses) {
            // If the given user paid for the expense
            if (expense.getPaidBy().equals(userId)) {
                for (SplitDetail split : expense.getSplitDetails()) {
                    if (!split.getUserId().equals(userId)) {
                        double currentAmount = settlementMap.getOrDefault(split.getUserId(), 0.0);
                        settlementMap.put(split.getUserId(), currentAmount + split.getAmount());
                    }
                }
            } else {
                // If someone else paid, check if the given user owes them
                for (SplitDetail split : expense.getSplitDetails()) {
                    if (split.getUserId().equals(userId)) {
                        double currentAmount = settlementMap.getOrDefault(expense.getPaidBy(), 0.0);
                        settlementMap.put(expense.getPaidBy(), currentAmount - split.getAmount());
                    }
                }
            }
        }

        List<SettlementsResDto> settlementList = new ArrayList<>();

        settlementMap.forEach((user, amount) -> {
            settlementList.add(new SettlementsResDto(user, amount));
        });

        return settlementList;
    }

}
