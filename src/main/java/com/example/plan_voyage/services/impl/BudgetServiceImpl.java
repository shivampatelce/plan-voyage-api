package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.*;
import com.example.plan_voyage.entity.*;
import com.example.plan_voyage.repository.*;
import com.example.plan_voyage.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SettlementRepository settlementRepository;

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
                .orElseThrow(() -> new RuntimeException("Invalid Trip Id"));

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
    public Expense editExpense(EditExpenseReqDto editExpenseReqDto) {
        Expense expense = expenseRepository.findById(editExpenseReqDto.getExpenseId())
                .orElseThrow(() -> new RuntimeException("Invalid expense id"));

        // Load existing split details
        List<SplitDetail> existingSplitDetails = new ArrayList<>(expense.getSplitDetails());

        // IDs coming from the request
        Set<UUID> incomingSplitDetailIds = editExpenseReqDto.getSplitDetails().stream()
                .map(EditSplitDetailDto::getSplitDetailId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Delete removed split details
        for (SplitDetail existing : existingSplitDetails) {
            if (!incomingSplitDetailIds.contains(existing.getSplitDetailId())) {
                splitDetailRepository.delete(existing);
            }
        }

        // Clear current split details to reassign
        expense.getSplitDetails().clear();

        // Add or update split details
        for (EditSplitDetailDto dto : editExpenseReqDto.getSplitDetails()) {
            SplitDetail splitDetail;

            if (dto.getSplitDetailId() == null) {
                // New
                splitDetail = new SplitDetail(dto.getUserId(), dto.getAmount(), expense);
            } else {
                // Existing
                splitDetail = splitDetailRepository.findById(dto.getSplitDetailId())
                        .orElseThrow(() -> new RuntimeException("Invalid split detail ID"));
                splitDetail.setUserId(dto.getUserId());
                splitDetail.setAmount(dto.getAmount());
            }

            expense.getSplitDetails().add(splitDetail);
        }

        // Update other fields
        expense.setTitle(editExpenseReqDto.getTitle());
        expense.setAmount(editExpenseReqDto.getAmount());
        expense.setCategory(editExpenseReqDto.getCategory());
        expense.setDate(editExpenseReqDto.getDate());
        expense.setPaidBy(editExpenseReqDto.getPaidBy());

        return expenseRepository.save(expense);
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

        List<Settlement> settlements = budget.getSettlements();

        for(Settlement settlement : settlements) {
            if(settlement.getPayer().equals(userId)) {
                double amount = settlementMap.getOrDefault(settlement.getPayee(), 0.0);
                settlementMap.put(settlement.getPayee(), amount + settlement.getAmount());
            } else {
                double amount = settlementMap.getOrDefault(settlement.getPayer(), 0.0);
                if(amount < 0) {
                    settlementMap.put(settlement.getPayer(), amount + settlement.getAmount());
                } else {
                    settlementMap.put(settlement.getPayer(), amount - settlement.getAmount());
                }
            }
        }

        List<SettlementsResDto> settlementList = new ArrayList<>();

        settlementMap.forEach((user, amount) -> {
            if(amount != 0){
                settlementList.add(new SettlementsResDto(user, amount));
            }
        });

        return settlementList;
    }

    @Override
    public Settlement newSettlement(NewSettlementReqDto newSettlementReqDto) {
        Trip trip = tripRepository.findById(newSettlementReqDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Invalid Trip Id"));

        Budget budget = budgetRepository.findByTrip(trip);

        Settlement settlement = new Settlement(newSettlementReqDto.getPayer(),
                                                newSettlementReqDto.getPayee(),
                                                newSettlementReqDto.getAmount(),
                                                budget);

        return settlementRepository.save(settlement);
    }

    @Override
    public List<Settlement> settlementActivities(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Invalid Trip Id"));

        Budget budget = budgetRepository.findByTrip(trip);

        return budget.getSettlements();
    }

    @Override
    public void deleteSettlementActivity(UUID settlementId) {
        settlementRepository.deleteById(settlementId);
    }

    @Override
    public Settlement editSettlementActivity(EditSettlementDto editSettlementDto) {

        Settlement settlement = settlementRepository.findById(editSettlementDto.getSettlementId())
                .orElseThrow(()-> new RuntimeException("Invalid settlement id: " + editSettlementDto.getSettlementId()));

        settlement.setPayee(editSettlementDto.getPayee());
        settlement.setPayer(editSettlementDto.getPayer());
        settlement.setAmount(editSettlementDto.getAmount());

        return settlementRepository.save(settlement);
    }

}
