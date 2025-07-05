package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {
}
