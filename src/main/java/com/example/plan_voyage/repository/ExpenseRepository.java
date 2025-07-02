package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
