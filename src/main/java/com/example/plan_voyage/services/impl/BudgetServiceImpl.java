package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.AddExpenseReqDto;
import com.example.plan_voyage.dto.SetBudgetReqDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
